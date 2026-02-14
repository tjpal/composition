package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeBackground
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeShapeShadow
import dev.tjpal.composition.diagrams.themes.tokens.CardBasicConfiguration
import dev.tjpal.composition.diagrams.themes.tokens.CardHeaderConfiguration
import dev.tjpal.composition.diagrams.themes.tokens.CardTokens

fun createCardConfiguration(): dev.tjpal.composition.diagrams.themes.tokens.CardTokens {
    return _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.CardTokens(
        basicConfiguration = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.CardBasicConfiguration(
            modifier = Modifier.Companion.defaultCascadeShapeShadow().defaultCascadeBackground(),
            fundamentalPadding = 8.dp,
            cardTileSize = (64 + 8).dp,
        ),
        headerConfiguration = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.CardHeaderConfiguration(
            headerHeight = 24.dp
        )
    )
}