package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.tokens.DividerType

data class DividerTheme(
    val modifier: Modifier = Modifier
)

data class DividerTokens(
    val primary: dev.tjpal.composition.diagrams.themes.tokens.DividerTheme = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerTheme(),
    val secondary: dev.tjpal.composition.diagrams.themes.tokens.DividerTheme = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerTheme()
) {
    fun getTheme(type: dev.tjpal.composition.diagrams.themes.tokens.DividerType): dev.tjpal.composition.diagrams.themes.tokens.DividerTheme {
        return when (type) {
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerType.PRIMARY -> primary
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerType.SECONDARY -> secondary
        }
    }
}

data class PagerTokens(
    val selectionBarHeight: Dp = 16.dp,
    val selectorSpacing: Dp = 0.dp,
    val contentPadding: PaddingValues = PaddingValues(0.dp),
    val selectedPageTypography: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography(),
    val unselectedPageTypography: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography()
)
