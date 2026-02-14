package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorCrossTokens
import dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorDotTokens
import dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorTokens
import dev.tjpal.composition.diagrams.themes.tokens.GraphEdgeTokens
import dev.tjpal.composition.diagrams.themes.tokens.GraphNodeTokens
import dev.tjpal.composition.diagrams.themes.tokens.GraphTokens

val DefaultNodeContentPadding = 6.dp
private val DefaultConnectedDotColor = Color(108, 108, 108, 255)

fun createGraphConfiguration(): dev.tjpal.composition.diagrams.themes.tokens.GraphTokens {
    return _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphTokens(
        node = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphNodeTokens(
            leftCornerBaseRadius = 6.dp,
            rightCornerBaseRadius = 6.dp,
            circularRadius = 32.dp,
            backgroundColor = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.BackgroundColor
        ),
        edge = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphEdgeTokens(
            strokeWidth = 1.dp,
            color = Color(202, 202, 202, 255)
        ),
        connector = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorTokens(
            connectedDot = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorDotTokens(
                _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.DefaultConnectedDotColor,
                radius = 6.dp,
                filled = true
            ),
            connectingDot = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorDotTokens(
                _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.DefaultConnectedDotColor,
                radius = 6.dp,
                filled = true
            ),
            notConnectedDot = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorDotTokens(
                Color(108, 108, 108, 255),
                radius = 6.dp,
                filled = false,
                strokeWidth = 1.dp
            ),
            inset = 6.dp,
            cross = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphConnectorCrossTokens(
                connectedColor = Color.White,
                notConnectedColor = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.DefaultConnectedDotColor,
                plusLengthFactor = 0.8f
            )
        )
    )
}
