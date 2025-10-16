package dev.tjpal.foundation.themes.cascade

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.tjpal.foundation.themes.tokens.*

class CascadeThemeData : ThemeTokens(
    background = BackgroundColor,
    buttons = buttonConfiguration(),
    typography = typographyConfiguration(),
    inputTheme = inputConfiguration(),
    dividerThemes = createDividerConfiguration(),
    tableTheme = createTableConfiguration(),
    pagerTheme = pagerConfiguration(typographyConfiguration()),
    cardTheme = createCardConfiguration(),
    heatmapTheme = createHeatmapConfiguration()
)

@Composable
fun Cascade( content: @Composable () -> Unit) {
    CompositionLocalProvider(Theme provides CascadeThemeData()) {
        content()
    }
}