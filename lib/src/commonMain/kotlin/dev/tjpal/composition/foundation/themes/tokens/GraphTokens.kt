package dev.tjpal.composition.foundation.themes.tokens

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

data class GraphConnectorDotTokens(
    val color: Color = Color(0xFF6C6C6C),
    val radius: Dp = 7.dp,
    val filled: Boolean = true,
    val strokeWidth: Dp = 1.dp,
    val hitRadius: Dp = 12.dp // clickable/hit area radius for connectors (slightly larger than visual radius)
)

/**
 * Tokens for connector behavior and visuals. The dot visuals are split by state so
 * callers can choose the appropriate appearance for connected / connecting / not connected.
 */
data class GraphConnectorTokens(
    val connectedDot: GraphConnectorDotTokens = GraphConnectorDotTokens(color = Color(0xFF2ECC71), radius = 6.dp),
    val connectingDot: GraphConnectorDotTokens = GraphConnectorDotTokens(color = Color(0xFFFFA726), radius = 6.dp),
    val notConnectedDot: GraphConnectorDotTokens = GraphConnectorDotTokens(color = Color(0xFF6C6C6C), radius = 6.dp),
    val inset: Dp = 4.dp // How many dp the connector is inset from the edge of the node
)

data class GraphTokens(
    val node: GraphNodeTokens = GraphNodeTokens(),
    val edge: GraphEdgeTokens = GraphEdgeTokens(),
    val connector: GraphConnectorTokens = GraphConnectorTokens()
)
