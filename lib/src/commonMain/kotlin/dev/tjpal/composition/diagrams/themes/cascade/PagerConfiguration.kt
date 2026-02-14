package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.tokens.PagerTokens
import dev.tjpal.composition.diagrams.themes.tokens.TextType
import dev.tjpal.composition.diagrams.themes.tokens.Typographies

fun pagerConfiguration(typographies: dev.tjpal.composition.diagrams.themes.tokens.Typographies): dev.tjpal.composition.diagrams.themes.tokens.PagerTokens {
    return _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.PagerTokens(
        selectionBarHeight = 16.dp,
        selectorSpacing = 4.dp,
        contentPadding = PaddingValues(8.dp),
        selectedPageTypography = typographies.getTypography(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PRIMARY),
        unselectedPageTypography = typographies.getTypography(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT)
    )
}