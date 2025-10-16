package dev.tjpal.foundation.themes.cascade

import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.TableTokens

fun createTableConfiguration(): TableTokens {
    return TableTokens(
        headerVerticalPadding = 4.dp,
        contentVerticalPadding = 4.dp,
        groupVerticalPadding = 6.dp
    )
}