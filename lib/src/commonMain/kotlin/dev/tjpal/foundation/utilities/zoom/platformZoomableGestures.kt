package dev.tjpal.foundation.utilities.zoom

import androidx.compose.ui.Modifier

// Platform specific implementations exist in their respective source sets
expect fun Modifier.platformZoomableGestures(
    state: ZoomableState,
    config: GestureConfig = GestureConfig()
): Modifier

fun Modifier.zoomableGestures(
    state: ZoomableState,
    config: GestureConfig = GestureConfig()
): Modifier = platformZoomableGestures(state, config)
