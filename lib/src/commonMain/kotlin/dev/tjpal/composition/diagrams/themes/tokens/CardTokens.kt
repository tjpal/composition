package dev.tjpal.composition.diagrams.themes.tokens

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

data class CardTokens(
    val basicConfiguration: dev.tjpal.composition.diagrams.themes.tokens.CardBasicConfiguration = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.CardBasicConfiguration(),
    val headerConfiguration: dev.tjpal.composition.diagrams.themes.tokens.CardHeaderConfiguration = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.CardHeaderConfiguration()
)
