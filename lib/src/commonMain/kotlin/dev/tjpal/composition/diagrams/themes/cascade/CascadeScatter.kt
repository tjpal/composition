package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.tokens.ScatterTokens

fun createScatterConfiguration(): ScatterTokens {
    return ScatterTokens(
        background = Color(0xFFF6F7F9),
        axisColor = Color(0xFF9E9E9E),
        axisStrokeWidth = 1.dp,
        gridColor = Color(0xFFE0E0E0),
        gridStrokeWidth = 1.dp,
        pointRadius = 6.dp,
        pointBorderWidth = 1.dp,
        pointBorderColor = Color.White,
    )
}
