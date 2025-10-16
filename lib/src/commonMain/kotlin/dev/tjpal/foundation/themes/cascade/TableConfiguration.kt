package dev.tjpal.foundation.themes.cascade

import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.TableTheme

fun createTableConfiguration(): TableTheme {
    return TableTheme(
        headerVerticalPadding = 4.dp,
        contentVerticalPadding = 4.dp,
        groupVerticalPadding = 6.dp
    )
}