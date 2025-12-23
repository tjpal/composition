package dev.tjpal.composition.foundation.structure.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.basics.functional.ZoomableBox
import dev.tjpal.composition.foundation.basics.spacing.LineGrid
import dev.tjpal.composition.foundation.themes.cascade.DefaultNodeContentPadding
import dev.tjpal.composition.foundation.themes.cascade.defaultCascadeBackground
import dev.tjpal.composition.foundation.themes.cascade.defaultCascadeShapeShadow
import dev.tjpal.composition.foundation.themes.tokens.GraphNodeTokens
import dev.tjpal.composition.foundation.themes.tokens.NodeShape
import dev.tjpal.composition.foundation.themes.tokens.Theme
import dev.tjpal.composition.foundation.themes.tokens.ThemeTokens
import dev.tjpal.composition.foundation.utilities.zoom.InitialScaleMode
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.sqrt

enum class EdgeSide {
    TOP, BOTTOM, LEFT, RIGHT
}

data class Connector(
    val id: String,
    val side: EdgeSide,
    val index: Int = 0,
    val positionProvider: ((node: NodeSpec, nodeWidthPx: Float, nodeHeightPx: Float, gridSpacingPx: Float) -> Offset)? = null
) {
    fun position(node: NodeSpec, nodeWidthPx: Float, nodeHeightPx: Float, gridSpacingPx: Float): Offset {
        positionProvider?.let { return it(node, nodeWidthPx, nodeHeightPx, gridSpacingPx) }
        return when (side) {
            EdgeSide.TOP -> {
                val x = (gridSpacingPx * (index + 0.5f)).coerceIn(0f, nodeWidthPx)
                Offset(x, 0f)
            }
            EdgeSide.BOTTOM -> {
                val x = (gridSpacingPx * (index + 0.5f)).coerceIn(0f, nodeWidthPx)
                Offset(x, nodeHeightPx)
            }
            EdgeSide.LEFT -> {
                val y = (gridSpacingPx * (index + 0.5f)).coerceIn(0f, nodeHeightPx)
                Offset(0f, y)
            }
            EdgeSide.RIGHT -> {
                val y = (gridSpacingPx * (index + 0.5f)).coerceIn(0f, nodeHeightPx)
                Offset(nodeWidthPx, y)
            }
        }
    }
}

data class EdgeSpec(
    val fromNodeId: String,
    val fromConnectorId: String,
    val toNodeId: String,
    val toConnectorId: String,
    val associatedData: Any? = null
)

data class NodeSpec(
    val id: String,
    val initialPosition: Offset,
    val widthMultiplier: Int = 4,
    val heightMultiplier: Int = 4,
    val connectors: List<Connector> = emptyList(),
    val associatedData: Any? = null,
    val shape: NodeShape = NodeShape.RECTANGLE,
    val content: @Composable (id: String) -> Unit
)

class GraphState(nodeSpecs: List<NodeSpec>) {
    val nodePositions = mutableStateMapOf<String, Offset>().apply {
        nodeSpecs.forEach { put(it.id, it.initialPosition) }
    }
}

private data class SelectedConnector(
    val nodeId: String,
    val connectorId: String,
    val position: Offset,
    val side: EdgeSide
)

@Composable
fun GraphEditor(
    modifier: Modifier = Modifier,
    state: GraphState,
    nodes: List<NodeSpec>,
    edges: List<EdgeSpec>,
    gridSpacing: Dp,
    gridExtension: Float,
    initialScaleMode: InitialScaleMode = InitialScaleMode.DEFAULT,
    onConnect: (fromNodeId: String, fromConnectorId: String, toNodeId: String, toConnectorId: String) -> Unit = { _, _, _, _ -> },
    onDisconnect: (nodeId: String, connectorId: String) -> Unit = { _, _ -> },
    onNodeDragFinished: (movedNode: NodeSpec, finalPosition: Offset) -> Unit = { _, _ -> }
) {
    val theme = Theme.current
    val density = LocalDensity.current

    // connecting state and pointer position used to render a temporary edge while connecting
    val selectedConnectorState = remember { mutableStateOf<SelectedConnector?>(null) }
    val currentPointerState = remember { mutableStateOf(Offset.Zero) }

    ZoomableBox(modifier = modifier.fillMaxSize(), initialScaleMode = initialScaleMode) {
        val gridRect = Rect(0f, 0f, gridExtension, gridExtension)

        LineGrid(spacing = gridSpacing, area = gridRect) {
            Canvas(Modifier.fillMaxSize().pointerInput(nodes, edges) {
                handleGraphPointerInput(
                    nodes = nodes,
                    state = state,
                    gridSpacing = gridSpacing,
                    density = density,
                    theme = theme,
                    selectedConnectorState = selectedConnectorState,
                    currentPointerState = currentPointerState,
                    onConnect = onConnect,
                    onDisconnect = onDisconnect
                )
            }) {
                val stroke = with(density) {
                    Stroke(width = theme.graph.edge.strokeWidth.toPx(), cap = StrokeCap.Round)
                }

                val gridSpacingPx = with(density) { gridSpacing.toPx() }
                drawExistingEdges(nodes = nodes, edges = edges, state = state, gridSpacingPx = gridSpacingPx, stroke = stroke, themeEdgeColor = theme.graph.edge.color)

                // Draw temporary connecting edge if a connector is selected
                val selected = selectedConnectorState.value
                if (selected != null) {
                    val currentPointer = currentPointerState.value
                    val pointerSide = sideFromDelta(currentPointer - selected.position)

                    drawEdgeBezierCurve(selected.position, currentPointer, selected.side, pointerSide, stroke, theme.graph.edge.color)
                }
            }

            // Nodes are drawn after the edges canvas so their content and connectors appear on top
            nodes.forEach { spec ->
                val pos by remember(spec.id) { derivedStateOf { state.nodePositions[spec.id] ?: Offset.Zero } }

                val width = gridSpacing * spec.widthMultiplier
                val height = gridSpacing * spec.heightMultiplier

                Node(
                    id = spec.id,
                    width = width,
                    height = height,
                    position = pos,
                    nodeSpec = spec,
                    gridSpacing = gridSpacing,
                    edges = edges,
                    onDragDelta = { delta ->
                        val current = state.nodePositions[spec.id] ?: Offset.Zero
                        state.nodePositions[spec.id] = current + delta
                    },
                    selectedConnector = selectedConnectorState.value,
                    onDragFinished = { onNodeDragFinished(spec, state.nodePositions[spec.id] ?: Offset.Zero) }
                ) {
                    spec.content(spec.id)
                }
            }
        }
    }
}

private suspend fun PointerInputScope.handleGraphPointerInput(
    nodes: List<NodeSpec>,
    state: GraphState,
    gridSpacing: Dp,
    density: Density,
    theme: ThemeTokens,
    selectedConnectorState: MutableState<SelectedConnector?>,
    currentPointerState: MutableState<Offset>,
    onConnect: (fromNodeId: String, fromConnectorId: String, toNodeId: String, toConnectorId: String) -> Unit,
    onDisconnect: (nodeId: String, connectorId: String) -> Unit
) {
    awaitPointerEventScope {
        // TODO: Is there a better way to detect double-tap/double-click in a pointerInput block?
        var lastDownTime: Long = 0L
        var lastDownNodeId: String? = null
        var lastDownConnectorId: String? = null
        val doubleTabTimeoutMS = 300L

        while (true) {
            val event = awaitPointerEvent()
            val p = event.changes.firstOrNull()?.position ?: continue
            currentPointerState.value = p

            // Build list of connector absolute positions for hit testing on each event
            val gridSpacingPx = with(density) { gridSpacing.toPx() }
            val connectorPositions = buildConnectorPositions(nodes, state, gridSpacingPx, density, theme)

            // If pointer was pressed -> evaluate hit
            if (event.changes.any { it.changedToDown() }) {
                val defaultHitRadiusPx = with(density) { theme.graph.connector.notConnectedDot.hitRadius.toPx() }
                val hit = findClosestConnector(p, connectorPositions, defaultHitRadiusPx)

                if (hit != null) {
                    val (node, connector, pos) = hit

                    // Detect double tap / double click: two downs on the same connector within timeout
                    val currentDownTime = event.changes.firstOrNull()?.uptimeMillis ?: 0L
                    val isDouble = lastDownNodeId == node.id &&
                        lastDownConnectorId == connector.id &&
                        (currentDownTime - lastDownTime) <= doubleTabTimeoutMS &&
                        lastDownTime != 0L

                    if (isDouble) {
                        onDisconnect(node.id, connector.id)
                        selectedConnectorState.value = null

                        // reset last down tracking
                        lastDownTime = 0L
                        lastDownNodeId = null
                        lastDownConnectorId = null

                        event.changes.forEach { it.consume() }
                        continue
                    } else {
                        lastDownTime = currentDownTime
                        lastDownNodeId = node.id
                        lastDownConnectorId = connector.id

                        val currentSelection = selectedConnectorState.value

                        if (currentSelection == null) {
                            // start connecting when clicking a not-connected connector
                            selectedConnectorState.value = SelectedConnector(nodeId = node.id, connectorId = connector.id, position = pos, side = connector.side)
                        } else {
                            // if second hit is different connector -> call onConnect and exit connecting mode
                            if (!(currentSelection.nodeId == node.id && currentSelection.connectorId == connector.id)) {
                                onConnect(currentSelection.nodeId, currentSelection.connectorId, node.id, connector.id)
                                selectedConnectorState.value = null
                            }
                        }
                    }

                    event.changes.forEach { it.consume() }
                } else {
                    // clicked outside any connector -> exit connecting mode
                    selectedConnectorState.value = null
                }
            }
        }
    }
}

private fun buildConnectorPositions(
    nodes: List<NodeSpec>,
    state: GraphState,
    gridSpacingPx: Float,
    density: Density,
    theme: ThemeTokens
): List<Triple<NodeSpec, Connector, Offset>> {
    val connectorPositions = mutableListOf<Triple<NodeSpec, Connector, Offset>>()

    nodes.forEach { node ->
        val nodePos = state.nodePositions[node.id] ?: Offset.Zero

        val nodeWidth = (gridSpacingPx * node.widthMultiplier)
        val nodeHeight = (gridSpacingPx * node.heightMultiplier)

        val insetPx = with(density) { theme.graph.connector.inset.toPx() }
        val halfGridPx = gridSpacingPx * 0.25f

        node.connectors.forEach { connector ->
            val final = computeConnectorFinalPosition(
                connector = connector,
                nodeSpec = node,
                nodeWidthPx = nodeWidth,
                nodeHeightPx = nodeHeight,
                gridSpacingPx = gridSpacingPx,
                insetPx = insetPx,
                orthogonalAmountPx = halfGridPx
            ) + nodePos

            connectorPositions.add(Triple(node, connector, final))
        }
    }

    return connectorPositions
}

private fun findClosestConnector(
    p: Offset,
    connectorPositions: List<Triple<NodeSpec, Connector, Offset>>,
    hitRadiusPx: Float
): Triple<NodeSpec, Connector, Offset>? {
    return connectorPositions.minByOrNull { (_, _, pos) ->
        val dx = pos.x - p.x
        val dy = pos.y - p.y
        dx * dx + dy * dy
    }?.let { (node, connector, pos) ->
        val dx = pos.x - p.x
        val dy = pos.y - p.y
        val dist = sqrt(dx * dx + dy * dy)
        if (dist <= hitRadiusPx) Triple(node, connector, pos) else null
    }
}

private fun DrawScope.drawExistingEdges(
    nodes: List<NodeSpec>,
    edges: List<EdgeSpec>,
    state: GraphState,
    gridSpacingPx: Float,
    stroke: Stroke,
    themeEdgeColor: Color
) {
    edges.forEach { edge ->
        val fromOwner = nodes.firstOrNull { it.id == edge.fromNodeId }
        val fromConnector = fromOwner?.connectors?.firstOrNull { it.id == edge.fromConnectorId }

        val toOwner = nodes.firstOrNull { it.id == edge.toNodeId }
        val toConnector = toOwner?.connectors?.firstOrNull { it.id == edge.toConnectorId }

        val startPoint = if (fromOwner != null && fromConnector != null) {
            val nodePos = state.nodePositions[fromOwner.id] ?: Offset.Zero
            val nodeWidthPx = (gridSpacingPx * fromOwner.widthMultiplier)
            val nodeHeightPx = (gridSpacingPx * fromOwner.heightMultiplier)
            nodePos + fromConnector.position(fromOwner, nodeWidthPx, nodeHeightPx, gridSpacingPx)
        } else Offset.Zero

        val endPoint = if (toOwner != null && toConnector != null) {
            val nodePos = state.nodePositions[toOwner.id] ?: Offset.Zero
            val nodeWidthPx = (gridSpacingPx * toOwner.widthMultiplier)
            val nodeHeightPx = (gridSpacingPx * toOwner.heightMultiplier)
            nodePos + toConnector.position(toOwner, nodeWidthPx, nodeHeightPx, gridSpacingPx)
        } else Offset.Zero

        if (startPoint != Offset.Zero && endPoint != Offset.Zero && fromConnector != null && toConnector != null) {
            drawEdgeBezierCurve(startPoint, endPoint, fromConnector.side, toConnector.side, stroke, themeEdgeColor)
        }
    }
}

private fun directionForSide(side: EdgeSide): Offset {
    return when (side) {
        EdgeSide.TOP -> Offset(0f, -1f)
        EdgeSide.BOTTOM -> Offset(0f, 1f)
        EdgeSide.LEFT -> Offset(-1f, 0f)
        EdgeSide.RIGHT -> Offset(1f, 0f)
    }
}

private fun orthogonalOffsetForSide(side: EdgeSide, amount: Float): Offset {
    return when (side) {
        EdgeSide.TOP -> Offset(-amount, 0f)
        EdgeSide.RIGHT -> Offset(0f, -amount)
        EdgeSide.BOTTOM -> Offset(amount, 0f)
        EdgeSide.LEFT -> Offset(0f, -amount)
    }
}

private fun sideFromDelta(delta: Offset): EdgeSide {
    val dx = delta.x
    val dy = delta.y
    return if (abs(dx) > abs(dy)) {
        if (dx > 0f) EdgeSide.RIGHT else EdgeSide.LEFT
    } else {
        if (dy > 0f) EdgeSide.BOTTOM else EdgeSide.TOP
    }
}

private fun DrawScope.drawEdgeBezierCurve(
    from: Offset,
    to: Offset,
    fromSide: EdgeSide,
    toSide: EdgeSide,
    stroke: Stroke,
    color: Color,
) {
    val dx = to.x - from.x
    val dy = to.y - from.y

    val dist = hypot(dx, dy)
    val controlLength = dist * 0.35f

    val direction1 = directionForSide(fromSide)
    val direction2 = directionForSide(toSide)

    val controlPoint1 = Offset(from.x + direction1.x * controlLength, from.y + direction1.y * controlLength)
    val controlPoint2 = Offset(to.x + direction2.x * controlLength, to.y + direction2.y * controlLength)

    val path = Path().apply {
        moveTo(from.x, from.y)
        cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, to.x, to.y)
    }

    drawPath(path = path, color = color, style = stroke)
}

private fun computeConnectorFinalPosition(
    connector: Connector,
    nodeSpec: NodeSpec,
    nodeWidthPx: Float,
    nodeHeightPx: Float,
    gridSpacingPx: Float,
    insetPx: Float,
    orthogonalAmountPx: Float
): Offset {
    val basePos = connector.position(nodeSpec, nodeWidthPx, nodeHeightPx, gridSpacingPx)
    val dir = directionForSide(connector.side)
    val inwardInset = Offset(-dir.x * insetPx, -dir.y * insetPx)
    val orthogonal = orthogonalOffsetForSide(connector.side, orthogonalAmountPx)
    return basePos + inwardInset + orthogonal
}

@Composable
private fun Node(
    id: String,
    width: Dp,
    height: Dp,
    position: Offset,
    nodeSpec: NodeSpec,
    gridSpacing: Dp,
    edges: List<EdgeSpec>,
    onDragDelta: (Offset) -> Unit,
    selectedConnector: SelectedConnector?,
    onDragFinished: () -> Unit,
    content: @Composable () -> Unit,
) {
    val theme = Theme.current

    val (sizeWidth, sizeHeight) = getNodeSize(width, height, nodeSpec)
    val nodeShape = getNodeShape(nodeSpec, theme.graph.node)

    // We need to keep it in a single modifier node. Otherwise, there will be issues with the transformation
    // since the neumorphism shadow modifier relies on drawBehind which seems to create a new layer with a
    // different transformation.
    val shapeModifier = Modifier
        .defaultCascadeShapeShadow(nodeShape)
        .defaultCascadeBackground(nodeShape)
        .padding(DefaultNodeContentPadding)

    Box(
        modifier = Modifier
            .graphicsLayer { translationX = position.x; translationY = position.y }
            .size(sizeWidth, sizeHeight)
            .then(shapeModifier)
            .pointerInput(id) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onDragDelta(Offset(dragAmount.x, dragAmount.y))
                    },
                    onDragEnd = {
                        onDragFinished()
                    }
                )
            }
    ) {
        content()

        // Draw connector dots on top of the node content so they are visible regardless of node content
        val densityCanvas = LocalDensity.current
        Canvas(modifier = Modifier.matchParentSize()) {
            val gridSpacingPx = with(densityCanvas) { gridSpacing.toPx() }
            val nodeWidthPx = size.width
            val nodeHeightPx = size.height

            val connectorInsetPx = with(densityCanvas) { theme.graph.connector.inset.toPx() }

            val halfGridPx = gridSpacingPx * 0.25f

            nodeSpec.connectors.forEach { connector ->
                val isConnected = edges.any { edge ->
                    (edge.fromNodeId == nodeSpec.id && edge.fromConnectorId == connector.id) ||
                        (edge.toNodeId == nodeSpec.id && edge.toConnectorId == connector.id)
                }

                // Only not-connected connectors can be the start of connecting mode; if selectedConnector references this, paint as connecting
                val dotTokens = if (selectedConnector?.nodeId == nodeSpec.id && selectedConnector.connectorId == connector.id) {
                    theme.graph.connector.connectingDot
                } else {
                    if (isConnected) theme.graph.connector.connectedDot else theme.graph.connector.notConnectedDot
                }

                val finalPos = computeConnectorFinalPosition(connector, nodeSpec, nodeWidthPx, nodeHeightPx, gridSpacingPx, connectorInsetPx, halfGridPx)

                drawCircle(
                    color = dotTokens.color,
                    radius = dotTokens.radius.toPx(),
                    center = finalPos,
                    style = if(dotTokens.filled) Fill else Stroke(width = dotTokens.strokeWidth.toPx())
                )
            }
        }
    }
}

private fun getNodeShape(nodeSpec: NodeSpec, nodeTokens: GraphNodeTokens): RoundedCornerShape {
    return when (nodeSpec.shape) {
        NodeShape.RECTANGLE -> RoundedCornerShape(0.dp)
        NodeShape.LEFT_ROUNDED -> RoundedCornerShape(
            topStart = nodeTokens.leftCornerBaseRadius * nodeSpec.heightMultiplier,
            bottomStart = nodeTokens.leftCornerBaseRadius * nodeSpec.heightMultiplier,
            topEnd = 0.dp,
            bottomEnd = 0.dp
        )
        NodeShape.RIGHT_ROUNDED -> RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = nodeTokens.leftCornerBaseRadius * nodeSpec.heightMultiplier,
            bottomEnd = nodeTokens.leftCornerBaseRadius * nodeSpec.heightMultiplier,
        )
        NodeShape.CIRCLE -> CircleShape
    }
}

private fun getNodeSize(width: Dp, height: Dp, nodeSpec: NodeSpec): Pair<Dp, Dp> {
    return if (nodeSpec.shape == NodeShape.CIRCLE) {
        val minDp = if (width < height) width else height
        Pair(minDp, minDp)
    } else {
        Pair(width, height)
    }
}
