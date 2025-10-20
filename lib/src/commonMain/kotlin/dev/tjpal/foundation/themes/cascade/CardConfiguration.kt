package dev.tjpal.foundation.themes.cascade

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.CardBasicConfiguration
import dev.tjpal.foundation.themes.tokens.CardHeaderConfiguration
import dev.tjpal.foundation.themes.tokens.CardTokens

fun createCardConfiguration(): CardTokens {
    return CardTokens(
        basicConfiguration = CardBasicConfiguration(
            modifier = Modifier.defaultCascadeShapeShadow().defaultCascadeBackground(),
            fundamentalPadding = 8.dp,
            cardTileSize = (64 + 8).dp,
        ),
        headerConfiguration = CardHeaderConfiguration(
            headerHeight = 24.dp
        )
    )
}