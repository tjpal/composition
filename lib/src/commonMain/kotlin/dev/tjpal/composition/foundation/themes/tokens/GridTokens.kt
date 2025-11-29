package dev.tjpal.composition.foundation.themes.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class GridPointTokens(
    val spacing: Dp = 24.dp,
    val dotRadius: Dp = 2.dp,
    val color: Color = Color(0xFFB0B0B0),
    val alpha: Float = 1f,
    val modifier: Modifier = Modifier
)

@Immutable
data class GridLineTokens(
    val spacing: Dp = 24.dp,
    val strokeWidth: Dp = 1.dp,
    val color: Color = Color(0xFFE0E0E0),
    val alpha: Float = 1f,
    val modifier: Modifier = Modifier
)

@Immutable
data class GridTokens(
    val point: GridPointTokens = GridPointTokens(),
    val line: GridLineTokens = GridLineTokens()
)
