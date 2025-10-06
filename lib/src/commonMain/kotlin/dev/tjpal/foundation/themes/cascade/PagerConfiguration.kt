package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.common.PagerTheme
import dev.tjpal.foundation.themes.common.TextType
import dev.tjpal.foundation.themes.common.Typographies

fun pagerConfiguration(typographies: Typographies): PagerTheme {
    return PagerTheme(
        selectionBarHeight = 16.dp,
        selectorSpacing = 4.dp,
        contentPadding = PaddingValues(8.dp),
        selectedPageTypography = typographies.getTypography(TextType.PRIMARY),
        unselectedPageTypography = typographies.getTypography(TextType.DEFAULT)
    )
}