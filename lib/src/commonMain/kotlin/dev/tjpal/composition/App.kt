package dev.tjpal.composition

import androidx.compose.runtime.Composable
import dev.tjpal.composition.diagrams.themes.cascade.Cascade
import dev.tjpal.composition.gallergy.Gallery

@Composable
fun App() {
    dev.tjpal.composition.diagrams.themes.cascade.Cascade {
        Gallery()
    }
}