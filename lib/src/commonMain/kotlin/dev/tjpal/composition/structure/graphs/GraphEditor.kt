package dev.tjpal.composition.structure.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.functional.ZoomableBox
import dev.tjpal.composition.foundation.spacing.LineGrid
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeBackground
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeShapeShadow
import dev.tjpal.composition.foundation.functional.zoom.InitialScaleMode
import kotlin.math.abs
import kotlin.math.hypot
import dev.tjpal.composition.diagrams.themes.cascade.DefaultNodeContentPadding
import dev.tjpal.composition.diagrams.themes.tokens.GraphNodeTokens
import dev.tjpal.composition.diagrams.themes.tokens.NodeShape
import dev.tjpal.composition.diagrams.themes.tokens.Theme

enum class EdgeSide {
    TOP, BOTTOM, LEFT, RIGHT
}

data class Connector(
    val id: String,
    val side: EdgeSide,
    val index: Int = 0,
    val positionProvider: ((node: NodeSpec, nodeWidthPx: Float, nodeHeightPx: Float, gridSpacingPx: Float) -> Offset)? = null,
    val allowsMultipleConnections: Boolean = false
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
    val onTap: ((id: String) -> Unit) = {},
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
    onNodeDragFinished: (movedNode: NodeSpec, finalPosition: Offset) -> Unit = { _, _ -> },
    onNodeTapped: (node: NodeSpec) -> Unit = {}
) {
    val theme = Theme.current
    val density = LocalDensity.current

    // connecting state and pointer position used to render a temporary edge while connecting
    val selectedConnectorState = remember { mutableStateOf<SelectedConnector?>(null) }
    val currentPointerState = remember { mutableStateOf(Offset.Zero) }

    ZoomableBox(
        modifier = modifier.fillMaxSize(),
        initialScaleMode = initialScaleMode
    ) {
        val gridRect = Rect(0f, 0f, gridExtension, gridExtension)

        LineGrid(spacing = gridSpacing, area = gridRect) {
            Canvas(Modifier.fillMaxSize().pointerInput(nodes, edges) {
                handleGraphPointerInput(
                    currentPointerState = currentPointerState,
                    selectedConnectorState = selectedConnectorState
                )
            }) {
                val stroke = with(density) {
                    Stroke(width = theme.graph.edge.strokeWidth.toPx(), cap = StrokeCap.Round)
                }

                val gridSpacingPx = with(density) { gridSpacing.toPx() }
                drawExistingEdges(
                    nodes = nodes,
                    edges = edges,
                    state = state,
                    gridSpacingPx = gridSpacingPx,
                    stroke = stroke,
                    themeEdgeColor = theme.graph.edge.color
                )

                // Draw temporary connecting edge if a connector is selected
                val selected = selectedConnectorState.value
                if (selected != null) {
                    val currentPointer = currentPointerState.value
                    val pointerSide = sideFromDelta(currentPointer - selected.position)

                    drawEdgeBezierCurve(
                        selected.position,
                        currentPointer,
                        selected.side,
                        pointerSide,
                        stroke,
                        theme.graph.edge.color
                    )
                }
            }

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
                    onDragFinished = { onNodeDragFinished(spec, state.nodePositions[spec.id] ?: Offset.Zero) },
                    onConnectorTap = { _, connectorId, side ->
                        handleConnectorTap(
                            spec,
                            connectorId,
                            side,
                            density,
                            gridSpacing,
                            state,
                            selectedConnectorState,
                            onConnect
                        )
                    },
                    onConnectorDoubleTap = { nodeId, connectorId ->
                        onDisconnect(nodeId, connectorId)
                        selectedConnectorState.value = null
                    },
                    onNodeTapped = onNodeTapped
                ) {
                    spec.content(spec.id)
                }
            }
        }
    }
}

private fun handleConnectorTap(
    nodeSpec: NodeSpec,
    connectorId: String,
    side: EdgeSide,
    density: Density,
    gridSpacing: Dp,
    state: GraphState,
    selectedConnectorState: MutableState<SelectedConnector?>,
    onConnect: (fromNodeId: String, fromConnectorId: String, toNodeId: String, toConnectorId: String) -> Unit
) {
    val gridSpacingPx = with(density) { gridSpacing.toPx() }
    val nodeWidthPx = gridSpacingPx * nodeSpec.widthMultiplier
    val nodeHeightPx = gridSpacingPx * nodeSpec.heightMultiplier

    val connector = nodeSpec.connectors.firstOrNull { it.id == connectorId } ?: return

    val absoluteCenter = (state.nodePositions[nodeSpec.id] ?: Offset.Zero) + connector.position(nodeSpec, nodeWidthPx, nodeHeightPx, gridSpacingPx)

    val currentSelection = selectedConnectorState.value

    if (currentSelection == null) {
        selectedConnectorState.value = SelectedConnector(nodeId = nodeSpec.id, connectorId = connectorId, position = absoluteCenter, side = side)
    } else {
        if (!(currentSelection.nodeId == nodeSpec.id && currentSelection.connectorId == connectorId)) {
            onConnect(currentSelection.nodeId, currentSelection.connectorId, nodeSpec.id, connectorId)
            selectedConnectorState.value = null
        }
    }
}

private suspend fun PointerInputScope.handleGraphPointerInput(
    currentPointerState: MutableState<Offset>,
    selectedConnectorState: MutableState<SelectedConnector?>
) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            val p = event.changes.firstOrNull()?.position ?: continue
            currentPointerState.value = p

            // If pointer was pressed on empty canvas -> exit connecting mode
            if (event.changes.any { it.changedToDown() }) {
                // A connector composable will consume tap events when tapped. If we receive a down event here,
                // it means the user tapped outside connectors (or the connector didn't consume) -> clear selection.
                selectedConnectorState.value = null
            }
        }
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
        EdgeSide.BOTTOM -> Offset(-amount, 0f)
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
    val inwardInset = insetShift(dir, insetPx)
    val orthogonal = orthogonalOffsetForSide(connector.side, orthogonalAmountPx)

    return basePos + inwardInset + orthogonal
}

private fun insetShift(dir: Offset, insetPx: Float) = Offset(
    if(abs(dir.y) < 0.01f) -insetPx else 0f,
    if(abs(dir.x) < 0.01f) -insetPx else 0f
)

@Composable
private fun ConnectorEndpoint(
    nodeId: String,
    connector: Connector,
    localCenterPx: Offset,
    isConnected: Boolean,
    dotColor: Color,
    radius: Dp,
    filled: Boolean,
    strokeWidth: Dp,
    allowsMultipleConnections: Boolean,
    canStartConnection: Boolean,
    side: EdgeSide,
    crossColor: Color,
    crossPlusFactor: Float,
    onPressed: (nodeId: String, connectorId: String, side: EdgeSide) -> Unit,
    onDoubleTapped: (nodeId: String, connectorId: String) -> Unit,
) {
    val density = LocalDensity.current
    val radiusPx = with(density) { radius.toPx() }
    val diameterDp = with(density) { (radiusPx * 2f).toDp() }

    val topLeftXp = localCenterPx.x - radiusPx
    val topLeftYp = localCenterPx.y - radiusPx

    val strokeWidthPx = with(density) { strokeWidth.toPx() }

    Box(
        modifier = Modifier
            .graphicsLayer { translationX = topLeftXp; translationY = topLeftYp }
            .size(diameterDp)
            .pointerInput(nodeId, connector.id, isConnected, canStartConnection) {
                if (isConnected) {
                    // Double-tabs are only relevant for connected connectors. Don't use them for unconnected connectors
                    // since the double-tab detection introduces a noticeable delay on single tabs.
                    detectTapGestures(
                        onTap = if (canStartConnection) {
                            {
                                onPressed(nodeId, connector.id, side)
                            }
                        } else null,
                        onDoubleTap = {
                            onDoubleTapped(nodeId, connector.id)
                        }
                    )
                } else {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            val firstDown = event.changes.firstOrNull { it.changedToDown() } ?: continue
                            firstDown.consume()

                            onPressed(nodeId, connector.id, side)
                        }
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2f

            if (filled) {
                drawCircle(color = dotColor, radius = radius, center = center)
            } else {
                drawCircle(color = dotColor, radius = radius, center = center, style = Stroke(width = strokeWidthPx))
            }

            if (allowsMultipleConnections) {
                drawMultipleConnectionsIndicator(radius, crossPlusFactor, crossColor, strokeWidthPx, center)
            }
        }
    }
}

fun DrawScope.drawMultipleConnectionsIndicator(radius: Float, crossPlusFactor: Float, crossColor: Color, strokeWidthPx: Float, center: Offset) {
    val plusLength = radius * crossPlusFactor
    val halfPlus = plusLength / 2f

    drawLine(
        color = crossColor,
        start = Offset(center.x - halfPlus, center.y),
        end = Offset(center.x + halfPlus, center.y),
        strokeWidth = strokeWidthPx
    )
    drawLine(
        color = crossColor,
        start = Offset(center.x, center.y - halfPlus),
        end = Offset(center.x, center.y + halfPlus),
        strokeWidth = strokeWidthPx
    )
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
    onConnectorTap: (nodeId: String, connectorId: String, side: EdgeSide) -> Unit,
    onConnectorDoubleTap: (nodeId: String, connectorId: String) -> Unit,
    onNodeTapped: (node: NodeSpec) -> Unit,
    content: @Composable () -> Unit,
) {
    val theme = Theme.current

    val (sizeWidth, sizeHeight) = getNodeSize(width, height, nodeSpec)
    val nodeShape = getNodeShape(nodeSpec, theme.graph.node)

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
                detectTapGestures(onTap = {
                    nodeSpec.onTap.invoke(id)
                    onNodeTapped(nodeSpec)
                })
            }
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

        val densityCanvas = LocalDensity.current
        val gridSpacingPx = with(densityCanvas) { gridSpacing.toPx() }
        val nodeWidthPx = with(densityCanvas) { sizeWidth.toPx() }
        val nodeHeightPx = with(densityCanvas) { sizeHeight.toPx() }

        val connectorInsetPx = with(densityCanvas) { theme.graph.connector.inset.toPx() }

        val halfGridPx = gridSpacingPx * 0.25f

        nodeSpec.connectors.forEach { connector ->
            val isConnected = connector.isConnected(edges, nodeSpec.id)
            val canStartConnection = connector.canStartNewConnection(edges, nodeSpec.id)

            val dotTokens = if (selectedConnector?.nodeId == nodeSpec.id && selectedConnector.connectorId == connector.id) {
                theme.graph.connector.connectingDot
            } else {
                if (isConnected) theme.graph.connector.connectedDot else theme.graph.connector.notConnectedDot
            }

            val crossTokens = theme.graph.connector.cross
            val isConnecting = selectedConnector?.nodeId == nodeSpec.id && selectedConnector.connectorId == connector.id
            val crossColor = if (connector.allowsMultipleConnections && (isConnected || isConnecting)) {
                crossTokens.connectedColor
            } else {
                crossTokens.notConnectedColor
            }

            val finalPos = computeConnectorFinalPosition(connector, nodeSpec, nodeWidthPx, nodeHeightPx, gridSpacingPx, connectorInsetPx, halfGridPx)

            ConnectorEndpoint(
                nodeId = nodeSpec.id,
                connector = connector,
                localCenterPx = finalPos,
                isConnected = isConnected,
                dotColor = dotTokens.color,
                radius = dotTokens.radius,
                filled = dotTokens.filled,
                strokeWidth = dotTokens.strokeWidth,
                allowsMultipleConnections = connector.allowsMultipleConnections,
                canStartConnection = canStartConnection,
                side = connector.side,
                crossColor = crossColor,
                crossPlusFactor = crossTokens.plusLengthFactor,
                onPressed = { nodeId, connectorId, side -> onConnectorTap(nodeId, connectorId, side) },
                onDoubleTapped = { nodeId, connectorId -> onConnectorDoubleTap(nodeId, connectorId) }
            )
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

internal fun Connector.isConnected(edges: List<EdgeSpec>, nodeId: String): Boolean {
    return edges.any { edge ->
        (edge.fromNodeId == nodeId && edge.fromConnectorId == id) ||
            (edge.toNodeId == nodeId && edge.toConnectorId == id)
    }
}

internal fun Connector.canStartNewConnection(edges: List<EdgeSpec>, nodeId: String): Boolean {
    val connected = isConnected(edges, nodeId)
    return !connected || allowsMultipleConnections
}
