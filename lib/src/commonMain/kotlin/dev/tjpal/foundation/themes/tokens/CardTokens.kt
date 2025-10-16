package dev.tjpal.foundation.themes.tokens

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CardBasicConfiguration(
    val cardTileSize: Dp = 48.dp,
    val fundamentalPadding: Dp = 8.dp,
    val modifier : Modifier = Modifier
)

data class CardHeaderConfiguration(
    val headerHeight: Dp = 16.dp
)

data class CardTheme(
    val basicConfiguration: CardBasicConfiguration = CardBasicConfiguration(),
    val headerConfiguration: CardHeaderConfiguration = CardHeaderConfiguration()
)
