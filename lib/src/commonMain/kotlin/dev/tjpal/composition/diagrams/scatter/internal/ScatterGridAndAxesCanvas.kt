package dev.tjpal.composition.diagrams.scatter.internal

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.tjpal.composition.diagrams.scatter.models.DiagramRange
import dev.tjpal.composition.diagrams.scatter.utilities.mapToPixel
import dev.tjpal.composition.foundation.themes.tokens.ScatterTokens

private fun DrawScope.verticalGrid(
    canvasSize: Size,
    gridStepsX: Int,
    tokens: ScatterTokens
) {
    for (i in 0..gridStepsX) {
        val x = (i.toFloat() / gridStepsX) * canvasSize.width

        drawLine(
            color = tokens.gridColor,
            start = Offset(x, 0f),
            end = Offset(x, canvasSize.height),
            strokeWidth = tokens.gridStrokeWidth.toPx()
        )
    }
}

private fun DrawScope.horizontalGrid(
    canvasSize: Size,
    gridStepsY: Int,
    tokens: ScatterTokens
) {
    for (i in 0..gridStepsY) {
        val y = (i.toFloat() / gridStepsY) * canvasSize.height

        drawLine(
            color = tokens.gridColor,
            start = Offset(0f, y),
            end = Offset(canvasSize.width, y),
            strokeWidth = tokens.gridStrokeWidth.toPx()
        )
    }
}

private fun DrawScope.xAxis(
    canvasSize: Size,
    range: DiagramRange,
    tokens: ScatterTokens
) {
    val zeroYInRange = range.minY <= 0f && range.maxY >= 0f

    val xAxisPx = if (zeroYInRange) {
        mapToPixel(range, canvasSize, 0f, 0f, 0f, 0f).y
    } else {
        if (range.minY > 0f) canvasSize.height else 0f
    }

    drawLine(
        color = tokens.axisColor,
        start = Offset(0f, xAxisPx),
        end = Offset(canvasSize.width, xAxisPx),
        strokeWidth = tokens.axisStrokeWidth.toPx()
    )
}

private fun DrawScope.yAxis(
    canvasSize: Size,
    range: DiagramRange,
    tokens: ScatterTokens
) {
    val zeroXInRange = range.minX <= 0f && range.maxX >= 0f

    val yAxisPx = if (zeroXInRange) {
        mapToPixel(range, canvasSize, 0f, 0f, 0f, 0f).x
    } else {
        if (range.minX > 0f) 0f else canvasSize.width
    }

    drawLine(
        color = tokens.axisColor,
        start = Offset(yAxisPx, 0f),
        end = Offset(yAxisPx, canvasSize.height),
        strokeWidth = tokens.axisStrokeWidth.toPx()
    )
}

@Composable
internal fun ScatterGridAndAxesCanvas(
    modifier: Modifier = Modifier,
    range: DiagramRange,
    gridStepsX: Int,
    gridStepsY: Int,
    tokens: ScatterTokens
) {
    Canvas(modifier = modifier) {
        val canvasSize = this.size

        verticalGrid(
            canvasSize = canvasSize,
            gridStepsX = gridStepsX,
            tokens = tokens
        )

        horizontalGrid(
            canvasSize = canvasSize,
            gridStepsY = gridStepsY,
            tokens = tokens
        )

        xAxis(
            canvasSize = canvasSize,
            range = range,
            tokens = tokens
        )

        yAxis(
            canvasSize = canvasSize,
            range = range,
            tokens = tokens
        )
    }
}
