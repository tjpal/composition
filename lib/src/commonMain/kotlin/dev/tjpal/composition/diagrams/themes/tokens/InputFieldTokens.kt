package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class InputFieldTokens(
    val modifier: Modifier = Modifier,
    val typography: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography(),
    val contentPadding: PaddingValues = PaddingValues(8.dp),
    val rowHeightFallback: Dp = 20.dp
)
