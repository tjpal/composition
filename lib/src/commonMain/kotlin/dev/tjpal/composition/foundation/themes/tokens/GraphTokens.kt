package dev.tjpal.composition.foundation.themes.tokens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class NodeShape {
    RECTANGLE,
    LEFT_ROUNDED,
    RIGHT_ROUNDED,
    CIRCLE
}

data class GraphNodeTokens(
    val leftCornerBaseRadius: Dp = 6.dp,
    val rightCornerBaseRadius: Dp = 6.dp,
    val circularRadius: Dp = 32.dp,
    val backgroundColor: Color = Color.White,
)

data class GraphEdgeTokens(
    val strokeWidth: Dp = 3.dp,
    val color: Color = Color(0xFF6C6C6C)
)

data class GraphConnectorDotTokens(
    val color: Color = Color(0xFF6C6C6C),
    val radius: Dp = 7.dp,
    val filled: Boolean = true,
    val strokeWidth: Dp = 1.dp,
    val hitRadius: Dp = 12.dp // clickable/hit area radius for connectors (slightly larger than visual radius)
)

data class GraphConnectorCrossTokens(
    val connectedColor: Color = Color(0xFF323232),
    val notConnectedColor: Color = Color(0xFFB0BEC5),
    val plusLengthFactor: Float = 0.8f
)

data class GraphConnectorTokens(
    val connectedDot: GraphConnectorDotTokens = GraphConnectorDotTokens(color = Color(0xFF2ECC71), radius = 6.dp),
    val connectingDot: GraphConnectorDotTokens = GraphConnectorDotTokens(color = Color(0xFFFFA726), radius = 6.dp),
    val notConnectedDot: GraphConnectorDotTokens = GraphConnectorDotTokens(color = Color(0xFF6C6C6C), radius = 6.dp),
    val inset: Dp = 4.dp, // How many dp the connector is inset from the edge of the node
    val cross: GraphConnectorCrossTokens = GraphConnectorCrossTokens()
)

data class GraphTokens(
    val node: GraphNodeTokens = GraphNodeTokens(),
    val edge: GraphEdgeTokens = GraphEdgeTokens(),
    val connector: GraphConnectorTokens = GraphConnectorTokens()
)
