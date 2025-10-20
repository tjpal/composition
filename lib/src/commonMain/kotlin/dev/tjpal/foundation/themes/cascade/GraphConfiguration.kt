package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.GraphEdgeTokens
import dev.tjpal.foundation.themes.tokens.GraphNodeTokens
import dev.tjpal.foundation.themes.tokens.GraphTokens

private val DefaultNodeContentPadding = 6.dp

fun createGraphConfiguration(): GraphTokens {
    return GraphTokens(
        node = GraphNodeTokens(
            modifier = Modifier.defaultCascadeShapeShadow().defaultCascadeBackground().padding(DefaultNodeContentPadding)
        ),
        edge = GraphEdgeTokens(
            strokeWidth = 1.dp,
            color = Color(202, 202, 202, 255)
        )
    )
}