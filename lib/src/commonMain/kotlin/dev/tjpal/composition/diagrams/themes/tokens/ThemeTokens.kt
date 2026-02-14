package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

open class ThemeTokens(
    val background: Color = Color.White,
    val button: ButtonTokens = ButtonTokens(),
    val floatingBar: FloatingBarTokens = FloatingBarTokens(),
    val typography: Typographies = Typographies(),
    val inputField: InputFieldTokens = InputFieldTokens(),
    val divider: DividerTokens = DividerTokens(),
    val table: TableTokens = TableTokens(),
    val pager: PagerTokens = PagerTokens(),
    val card: CardTokens = CardTokens(),
    val heatmap: HeatmapTokens = HeatmapTokens(),
    val scatter: ScatterTokens = ScatterTokens(),
    val grid: GridTokens = GridTokens(),
    val graph: GraphTokens = GraphTokens(),
    val waiting: WaitingTokens = WaitingTokens(),
    val funnel: FunnelTokens = FunnelTokens()
)

val Theme = compositionLocalOf { ThemeTokens() }
