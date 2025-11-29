package dev.tjpal.composition.foundation.basics.functional

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tjpal.composition.foundation.themes.tokens.Theme

@Composable
fun Input(modifier: Modifier = Modifier) {
    Box(modifier = modifier.then(Theme.current.inputField.modifier))
}