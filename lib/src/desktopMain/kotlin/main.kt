import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.tjpal.gallergy.Gallery

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Gallery()
    }
}