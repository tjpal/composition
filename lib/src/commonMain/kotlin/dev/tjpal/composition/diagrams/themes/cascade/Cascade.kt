package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.tjpal.composition.diagrams.themes.tokens.Theme
import dev.tjpal.composition.diagrams.themes.tokens.ThemeTokens

class CascadeThemeData : dev.tjpal.composition.diagrams.themes.tokens.ThemeTokens(
    background = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.BackgroundColor,
    button = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.buttonConfiguration(),
    floatingBar = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createFloatingBarConfiguration(),
    typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.typographyConfiguration(),
    inputField = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.inputConfiguration(),
    divider = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createDividerConfiguration(),
    table = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createTableConfiguration(),
    pager = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.pagerConfiguration(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.typographyConfiguration()),
    card = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createCardConfiguration(),
    heatmap = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createHeatmapConfiguration(),
    scatter = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createScatterConfiguration(),
    graph = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createGraphConfiguration(),
    funnel = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.createFunnelConfiguration()
)

@Composable
fun Cascade(content: @Composable () -> Unit) {
    CompositionLocalProvider(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme provides _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.CascadeThemeData()) {
        content()
    }
}
