package dev.tjpal.foundation.themes.tokens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DividerTheme(
    val modifier: Modifier = Modifier
)

data class DividerThemes(
    val primary: DividerTheme = DividerTheme(),
    val secondary: DividerTheme = DividerTheme()
) {
    fun getTheme(type: DividerType): DividerTheme {
        return when (type) {
            DividerType.PRIMARY -> primary
            DividerType.SECONDARY -> secondary
        }
    }
}

data class PagerTheme(
    val selectionBarHeight: Dp = 16.dp,
    val selectorSpacing: Dp = 0.dp,
    val contentPadding: PaddingValues = PaddingValues(0.dp),
    val selectedPageTypography: Typography = Typography(),
    val unselectedPageTypography: Typography = Typography()
)
