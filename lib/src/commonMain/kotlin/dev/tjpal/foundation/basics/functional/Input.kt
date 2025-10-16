package dev.tjpal.foundation.basics.functional

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tjpal.foundation.themes.tokens.Theme

@Composable
fun Input(modifier: Modifier = Modifier) {
    Box(modifier = modifier.then(Theme.current.inputTheme.modifier))
}