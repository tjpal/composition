package dev.tjpal.foundation.structure.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import dev.tjpal.foundation.basics.functional.ZoomableBox
import dev.tjpal.foundation.basics.spacing.LineGrid
import dev.tjpal.foundation.utilities.zoom.InitialScaleMode
import dev.tjpal.foundation.themes.tokens.Theme
import kotlin.math.hypot

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
)

data class NodeSpec(
    val id: String,
    val initialPosition: Offset,
    val widthMultiplier: Int = 4,
    val heightMultiplier: Int = 4,
    val connectors: List<Connector> = emptyList(),
    val content: @Composable (id: String) -> Unit
)

class GraphState(nodeSpecs: List<NodeSpec>) {
    val nodePositions = mutableStateMapOf<String, Offset>().apply {
        nodeSpecs.forEach { put(it.id, it.initialPosition) }
    }
}

@Composable
fun GraphEditor(
    modifier: Modifier = Modifier,
    state: GraphState,
    nodes: List<NodeSpec>,
    edges: List<EdgeSpec>,
    gridSpacing: Dp,
    gridExtension: Float,
    initialScaleMode: InitialScaleMode = InitialScaleMode.DEFAULT
) {
    val theme = Theme.current
    val density = LocalDensity.current

    ZoomableBox(modifier = modifier.fillMaxSize(), initialScaleMode = initialScaleMode) {
        val gridRect = Rect(0f, 0f, gridExtension, gridExtension)

        LineGrid(spacing = gridSpacing, area = gridRect) {
            Canvas(Modifier.fillMaxSize()) {
                val stroke = with(density) {
                    Stroke(width = theme.graph.edge.strokeWidth.toPx(), cap = StrokeCap.Round)
                }

                val gridSpacingPx = with(density) { gridSpacing.toPx() }

                edges.forEach { edge ->
                    val fromOwner = nodes.firstOrNull { it.id == edge.fromNodeId }
                    val fromConnector = fromOwner?.connectors?.firstOrNull { it.id == edge.fromConnectorId }

                    val toOwner = nodes.firstOrNull { it.id == edge.toNodeId }
                    val toConnector = toOwner?.connectors?.firstOrNull { it.id == edge.toConnectorId }

                    val startPoint = if (fromOwner != null && fromConnector != null) {
                        val nodePos = state.nodePositions[fromOwner.id] ?: Offset.Zero
                        val nodeWidthPx = with(density) { (gridSpacing * fromOwner.widthMultiplier).toPx() }
                        val nodeHeightPx = with(density) { (gridSpacing * fromOwner.heightMultiplier).toPx() }
                        nodePos + fromConnector.position(fromOwner, nodeWidthPx, nodeHeightPx, gridSpacingPx)
                    } else Offset.Zero

                    val endPoint = if (toOwner != null && toConnector != null) {
                        val nodePos = state.nodePositions[toOwner.id] ?: Offset.Zero
                        val nodeWidthPx = with(density) { (gridSpacing * toOwner.widthMultiplier).toPx() }
                        val nodeHeightPx = with(density) { (gridSpacing * toOwner.heightMultiplier).toPx() }
                        nodePos + toConnector.position(toOwner, nodeWidthPx, nodeHeightPx, gridSpacingPx)
                    } else Offset.Zero

                    if (startPoint != Offset.Zero && endPoint != Offset.Zero && fromConnector != null && toConnector != null) {
                        drawEdgeBezierCurve(startPoint, endPoint, fromConnector.side, toConnector.side, stroke, theme.graph.edge.color)
                    }
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
                    nodeModifier = theme.graph.node.modifier,
                    onDragDelta = { delta ->
                        val current = state.nodePositions[spec.id] ?: Offset.Zero
                        state.nodePositions[spec.id] = current + delta
                    }
                ) {
                    spec.content(spec.id)
                }
            }
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

@Composable
private fun Node(
    id: String,
    width: Dp,
    height: Dp,
    position: Offset,
    nodeModifier: Modifier = Modifier,
    onDragDelta: (Offset) -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.
            graphicsLayer { translationX = position.x; translationY = position.y }.
            size(width = width, height = height).
            then(nodeModifier).
            pointerInput(id) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDragDelta(Offset(dragAmount.x, dragAmount.y))
                }
            }
    ) {
        content()
    }
}
