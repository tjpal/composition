package dev.tjpal.composition.foundation.themes.tokens

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class FunnelTokens(
    val background: Color = Color.White,
    val stageSeparatorColor: Color = Color(0xFF6C6C6C),
    val stageSeparatorStrokeWidth: Dp = 3.dp,
    val innerSeparatorColor: Color = Color(0xFFBDBDBD),
    val innerSeparatorStrokeWidth: Dp = 1.dp,
    val tooltipShapeModifier: Modifier = Modifier,
    val itemSpacing: Dp = 4.dp,
    val itemMinDiameter: Dp = 6.dp,
    val itemDefaultCellPadding: Dp = 4.dp
)
