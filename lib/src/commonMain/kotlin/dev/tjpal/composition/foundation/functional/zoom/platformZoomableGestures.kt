package dev.tjpal.composition.foundation.functional.zoom

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.zoomableGestures(
    state: dev.tjpal.composition.foundation.functional.zoom.ZoomableState,
    config: dev.tjpal.composition.foundation.functional.zoom.GestureConfig = _root_ide_package_.dev.tjpal.composition.foundation.functional.zoom.GestureConfig()
): Modifier = this.pointerInput(state, config) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()

            if (event.type == PointerEventType.Scroll) {
                val scrollDelta = event.changes.firstOrNull()?.scrollDelta ?: Offset.Zero

                if (config.wheelZooms) {
                    val deltaScale = 1f + (-scrollDelta.y) * config.wheelZoomFactor
                    state.onTransformDelta(deltaScale, Offset.Zero, null)
                }

                event.changes.forEach { it.consume() }
            }
        }
    }
}.pointerInput(state, config) {
    detectTransformGestures { centroid, pan, zoom, _ ->
        state.onTransformDelta(zoom, Offset(pan.x, pan.y), centroid)
    }
}