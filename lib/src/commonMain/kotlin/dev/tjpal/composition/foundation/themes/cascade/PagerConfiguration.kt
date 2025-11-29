package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.themes.tokens.PagerTokens
import dev.tjpal.composition.foundation.themes.tokens.TextType
import dev.tjpal.composition.foundation.themes.tokens.Typographies

fun pagerConfiguration(typographies: Typographies): PagerTokens {
    return PagerTokens(
        selectionBarHeight = 16.dp,
        selectorSpacing = 4.dp,
        contentPadding = PaddingValues(8.dp),
        selectedPageTypography = typographies.getTypography(TextType.PRIMARY),
        unselectedPageTypography = typographies.getTypography(TextType.DEFAULT)
    )
}