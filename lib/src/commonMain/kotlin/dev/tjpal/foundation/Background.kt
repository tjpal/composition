package dev.tjpal.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tjpal.foundation.themes.tokens.Theme

@Composable
fun Background(modifier: Modifier = Modifier, content : @Composable () -> Unit) {
    Box(modifier = modifier.background(Theme.current.background).fillMaxSize()) {
        content()
    }
}