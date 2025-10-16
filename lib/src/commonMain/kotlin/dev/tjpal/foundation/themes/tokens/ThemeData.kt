package dev.tjpal.foundation.themes.tokens

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

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