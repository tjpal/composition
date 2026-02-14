package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

open class ThemeTokens(
    val background: Color = Color.White,
    val button: dev.tjpal.composition.diagrams.themes.tokens.ButtonTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonTokens(),
    val floatingBar: dev.tjpal.composition.diagrams.themes.tokens.FloatingBarTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FloatingBarTokens(),
    val typography: dev.tjpal.composition.diagrams.themes.tokens.Typographies = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typographies(),
    val inputField: dev.tjpal.composition.diagrams.themes.tokens.InputFieldTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.InputFieldTokens(),
    val divider: dev.tjpal.composition.diagrams.themes.tokens.DividerTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerTokens(),
    val table: dev.tjpal.composition.diagrams.themes.tokens.TableTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TableTokens(),
    val pager: dev.tjpal.composition.diagrams.themes.tokens.PagerTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.PagerTokens(),
    val card: dev.tjpal.composition.diagrams.themes.tokens.CardTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.CardTokens(),
    val heatmap: dev.tjpal.composition.diagrams.themes.tokens.HeatmapTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.HeatmapTokens(),
    val scatter: dev.tjpal.composition.diagrams.themes.tokens.ScatterTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ScatterTokens(),
    val grid: dev.tjpal.composition.diagrams.themes.tokens.GridTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GridTokens(),
    val graph: dev.tjpal.composition.diagrams.themes.tokens.GraphTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.GraphTokens(),
    val waiting: dev.tjpal.composition.diagrams.themes.tokens.WaitingTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.WaitingTokens(),
    val funnel: dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens()
)

val Theme = compositionLocalOf { _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ThemeTokens() }
