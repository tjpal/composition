package dev.tjpal.composition.foundation.themes.tokens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ScatterTokens(
    val background: Color = Color(0xFFF6F7F9),
    val axisColor: Color = Color(0xFFBDBDBD),
    val axisStrokeWidth: Dp = 1.dp,
    val gridColor: Color = Color(0xFFE6E7EA),
    val gridStrokeWidth: Dp = 1.dp,
    val pointRadius: Dp = 6.dp,
    val pointBorderWidth: Dp = 1.dp,
    val pointBorderColor: Color = Color.White,
    val tooltipShiftX: Float = 8.0f,
    val tooltipShiftY: Float = -8.0f,
    val tooltipShapeModifier: Modifier = Modifier.background(Color.White, shape = RoundedCornerShape(4.dp)).padding(6.dp)
)
