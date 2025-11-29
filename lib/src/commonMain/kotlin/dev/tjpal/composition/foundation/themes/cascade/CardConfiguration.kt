package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.themes.tokens.CardBasicConfiguration
import dev.tjpal.composition.foundation.themes.tokens.CardHeaderConfiguration
import dev.tjpal.composition.foundation.themes.tokens.CardTokens

fun createCardConfiguration(): CardTokens {
    return CardTokens(
        basicConfiguration = CardBasicConfiguration(
            modifier = Modifier.Companion.defaultCascadeShapeShadow().defaultCascadeBackground(),
            fundamentalPadding = 8.dp,
            cardTileSize = (64 + 8).dp,
        ),
        headerConfiguration = CardHeaderConfiguration(
            headerHeight = 24.dp
        )
    )
}