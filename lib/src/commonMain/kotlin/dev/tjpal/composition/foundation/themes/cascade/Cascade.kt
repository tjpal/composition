package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.tjpal.composition.foundation.themes.tokens.Theme
import dev.tjpal.composition.foundation.themes.tokens.ThemeTokens

class CascadeThemeData : ThemeTokens(
    background = BackgroundColor,
    button = buttonConfiguration(),
    floatingBar = createFloatingBarConfiguration(),
    typography = typographyConfiguration(),
    inputField = inputConfiguration(),
    divider = createDividerConfiguration(),
    table = createTableConfiguration(),
    pager = pagerConfiguration(typographyConfiguration()),
    card = createCardConfiguration(),
    heatmap = createHeatmapConfiguration(),
    scatter = createScatterConfiguration(),
    graph = createGraphConfiguration(),
    funnel = createFunnelConfiguration()
)

@Composable
fun Cascade(content: @Composable () -> Unit) {
    CompositionLocalProvider(Theme provides CascadeThemeData()) {
        content()
    }
}
