package dev.tjpal.foundation.themes.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Typography(
    val style: TextStyle = TextStyle()
)

data class Typographies(
    val primary: Typography = Typography(),
    val default: Typography = Typography(),
    val link: Typography = Typography()
) {
    fun getTypography(type: TextType): Typography {
        return when (type) {
            TextType.PRIMARY -> primary
            TextType.DEFAULT -> default
        }
    }
}

data class ButtonStateTheme(
    val modifier: Modifier = Modifier
)

data class ButtonTheme(
    val default: ButtonStateTheme = ButtonStateTheme(),
    val pressed: ButtonStateTheme = ButtonStateTheme(),
)

data class ButtonThemes(
    val primaryButton: ButtonTheme = ButtonTheme(),
    val primaryText: Typography = Typography(),
    val defaultText: Typography = Typography()
) {
    fun getButtonTheme(type: ButtonType): ButtonTheme {
        return when (type) {
            ButtonType.PRIMARY -> primaryButton
            ButtonType.DEFAULT -> primaryButton
        }
    }
}

data class InputTheme(
    val modifier: Modifier = Modifier
)

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

data class TableTheme(
    val headerVerticalPadding: Dp = 0.dp,
    val contentVerticalPadding: Dp = 0.dp,
    val groupVerticalPadding: Dp = 0.dp
)

data class CardBasicConfiguration(
    val cardTileSize: Dp = 48.dp,
    val fundamentalPadding: Dp = 8.dp,
    val modifier : Modifier = Modifier
)

data class CardHeaderConfiguration(
    val headerHeight: Dp = 16.dp
)

data class CardTheme(
    val basicConfiguration: CardBasicConfiguration = CardBasicConfiguration(),
    val headerConfiguration: CardHeaderConfiguration = CardHeaderConfiguration()
)

data class HeatmapTheme(
    val cellSize: Dp = 24.dp,
    val cellPadding: Dp = 4.dp
)

open class ThemeData(
    val background: Color = Color.White,
    val buttons: ButtonThemes = ButtonThemes(),
    val typography: Typographies = Typographies(),
    val inputTheme: InputTheme = InputTheme(),
    val dividerThemes: DividerThemes = DividerThemes(),
    val tableTheme: TableTheme = TableTheme(),
    val pagerTheme: PagerTheme = PagerTheme(),
    val cardTheme: CardTheme = CardTheme(),
    val heatmapTheme: HeatmapTheme = HeatmapTheme(),
)

val Theme = compositionLocalOf { ThemeData() }