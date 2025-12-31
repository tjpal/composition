package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.themes.tokens.GraphConnectorDotTokens
import dev.tjpal.composition.foundation.themes.tokens.GraphConnectorTokens
import dev.tjpal.composition.foundation.themes.tokens.GraphEdgeTokens
import dev.tjpal.composition.foundation.themes.tokens.GraphNodeTokens
import dev.tjpal.composition.foundation.themes.tokens.GraphTokens

val DefaultNodeContentPadding = 6.dp

fun createGraphConfiguration(): GraphTokens {
    return GraphTokens(
        node = GraphNodeTokens(
            leftCornerBaseRadius = 6.dp,
            rightCornerBaseRadius = 6.dp,
            circularRadius = 32.dp,
            backgroundColor = BackgroundColor
        ),
        edge = GraphEdgeTokens(
            strokeWidth = 1.dp,
            color = Color(202, 202, 202, 255)
        ),
        connector = GraphConnectorTokens(
            connectedDot = GraphConnectorDotTokens(Color(108, 108, 108, 255), radius = 6.dp, filled = true),
            connectingDot = GraphConnectorDotTokens(Color(108, 108, 108, 255), radius = 6.dp, filled = true),
            notConnectedDot = GraphConnectorDotTokens(
                Color(108, 108, 108, 255),
                radius = 6.dp,
                filled = false,
                strokeWidth = 1.dp
            ),
            inset = 6.dp
        )
    )
}