package dev.tjpal.composition.diagrams.funnel.internal

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.tjpal.composition.foundation.themes.tokens.FunnelTokens

private fun DrawScope.drawInnerSeparators(
    canvasSize: Size,
    totalSubStages: Int,
    categories: Int,
    tokens: FunnelTokens
) {
    // Vertical separator within a stage (thin)
    for (i in 0..totalSubStages) {
        val x = (i.toFloat() / totalSubStages) * canvasSize.width

        drawLine(
            color = tokens.innerSeparatorColor,
            start = Offset(x, 0f),
            end = Offset(x, canvasSize.height),
            strokeWidth = tokens.innerSeparatorStrokeWidth.toPx()
        )
    }

    // Horizontal separators between categories (thin)
    for (i in 0..categories) {
        val y = (i.toFloat() / categories) * canvasSize.height

        drawLine(
            color = tokens.innerSeparatorColor,
            start = Offset(0f, y),
            end = Offset(canvasSize.width, y),
            strokeWidth = tokens.innerSeparatorStrokeWidth.toPx()
        )
    }
}

private fun DrawScope.drawStageSeparators(canvasSize: Size, stages: Int, tokens: FunnelTokens) {
    for (i in 0..stages) {
        val x = (i.toFloat() / stages) * canvasSize.width

        drawLine(
            color = tokens.stageSeparatorColor,
            start = Offset(x, 0f),
            end = Offset(x, canvasSize.height),
            strokeWidth = tokens.stageSeparatorStrokeWidth.toPx()
        )
    }
}

@Composable
internal fun FunnelGridCanvas(
    modifier: Modifier = Modifier,
    stages: Int,
    subStagesPerStage: Int,
    categories: Int,
    tokens: FunnelTokens
) {
    if (stages <= 0 || subStagesPerStage <= 0 || categories <= 0) {
        return
    }

    Canvas(modifier = modifier) {
        if (size.width <= 0f || size.height <= 0f) {
            return@Canvas
        }

        val totalSubStages = stages * subStagesPerStage

        drawInnerSeparators(
            canvasSize = size,
            totalSubStages = totalSubStages,
            categories = categories,
            tokens = tokens
        )

        drawStageSeparators(
            canvasSize = size,
            stages = stages,
            tokens = tokens
        )
    }
}
