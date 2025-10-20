package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.GraphEdgeTokens
import dev.tjpal.foundation.themes.tokens.GraphNodeTokens
import dev.tjpal.foundation.themes.tokens.GraphTokens

fun createGraphConfiguration(): GraphTokens {
    return GraphTokens(
        node = GraphNodeTokens(
            modifier = Modifier.shapeShadow(
                highlightShadowColor = HighlightShadowColor,
                castShadowColor = CastShadowColor,
                blurRadius = OutsetShadowBlurRadius,
                offsetX = OutsetShadowOffsetX,
                offsetY = OutsetShadowOffsetY,
                shape = RoundedCornerShape(DefaultCornerRadius)
            ).
            background(
                BackgroundColor,
                RoundedCornerShape(DefaultCornerRadius)
            )
        ),
        edge = GraphEdgeTokens(
            strokeWidth = 1.dp,
            color = Color(202, 202, 202, 255)
        )
    )
}