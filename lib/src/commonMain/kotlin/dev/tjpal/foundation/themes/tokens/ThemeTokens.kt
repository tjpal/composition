package dev.tjpal.foundation.themes.tokens

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

open class ThemeTokens(
    val background: Color = Color.White,
    val button: ButtonTokens = ButtonTokens(),
    val typography: Typographies = Typographies(),
    val inputField: InputFieldTokens = InputFieldTokens(),
    val divider: DividerTokens = DividerTokens(),
    val table: TableTokens = TableTokens(),
    val pager: PagerTokens = PagerTokens(),
    val card: CardTokens = CardTokens(),
    val heatmap: HeatmapTokens = HeatmapTokens(),
    val grid: GridTokens = GridTokens(),
)

val Theme = compositionLocalOf { ThemeTokens() }
