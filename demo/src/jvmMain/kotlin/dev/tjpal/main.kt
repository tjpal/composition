package dev.tjpal

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.tjpal.composition.diagrams.themes.cascade.Cascade
import dev.tjpal.composition.gallergy.Gallery

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "composition",
    ) {
        Cascade {
            Gallery()
        }
    }
}