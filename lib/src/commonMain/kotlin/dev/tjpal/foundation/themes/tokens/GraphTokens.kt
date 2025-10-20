package dev.tjpal.foundation.themes.tokens

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class GraphNodeTokens(
    val modifier: Modifier = Modifier,
    val defaultSizeMultiplier: Int = 4
)

data class GraphEdgeTokens(
    val strokeWidth: Dp = 3.dp,
    val color: Color = Color(0xFF6C6C6C)
)

data class GraphTokens(
    val node: GraphNodeTokens = GraphNodeTokens(),
    val edge: GraphEdgeTokens = GraphEdgeTokens()
)
