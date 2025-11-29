package dev.tjpal

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.tjpal.composition.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "composition",
    ) {
        App()
    }
}