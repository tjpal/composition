package dev.tjpal

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.tjpal.composition.diagrams.themes.cascade.Cascade
import dev.tjpal.composition.gallergy.Gallery

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        Cascade {
            Gallery()
        }
    }
}