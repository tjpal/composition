package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.tokens.TableTokens

fun createTableConfiguration(): TableTokens {
    return TableTokens(
        headerVerticalPadding = 4.dp,
        contentVerticalPadding = 4.dp,
        groupVerticalPadding = 6.dp
    )
}