package dev.tjpal.composition

import androidx.compose.runtime.Composable
import dev.tjpal.composition.foundation.themes.cascade.Cascade
import dev.tjpal.composition.gallergy.Gallery

@Composable
fun App() {
    Cascade {
        Gallery()
    }
}