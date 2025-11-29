package dev.tjpal.composition.foundation.utilities.zoom

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset

enum class InitialScaleMode {
    DEFAULT,
    FIT
}

@Stable
abstract class ZoomableState {
    abstract val scale: Float
    abstract val offset: Offset
    abstract val minScale: Float
    abstract val maxScale: Float

    abstract fun snapTo(scale: Float, offset: Offset)
    abstract fun panBy(delta: Offset)

    abstract fun onTransformDelta(deltaScale: Float, panDelta: Offset, centroid: Offset? = null)
    abstract fun reset()
}

@Immutable
data class GestureConfig(
    val wheelZooms: Boolean = true,
    val wheelZoomFactor: Float = 0.015f
)

@Immutable
data class ZoomConfig(
    val initialScale: Float = 1f,
    val minScale: Float = 0.1f,
    val maxScale: Float = 4f,
)

@Composable
fun rememberZoomableState(config: ZoomConfig = ZoomConfig()): ZoomableState {
    return remember {
        object : ZoomableState() {
            private var internalScale by mutableStateOf(config.initialScale)
            private var internalOffset by mutableStateOf(Offset.Zero)

            override val scale: Float get() = internalScale
            override val offset: Offset get() = internalOffset
            override val minScale: Float get() = config.minScale
            override val maxScale: Float get() = config.maxScale

            override fun snapTo(scale: Float, offset: Offset) {
                internalScale = scale.coerceIn(minScale, maxScale)
                internalOffset = offset
            }

            override fun panBy(delta: Offset) {
                internalOffset += delta
            }

            override fun onTransformDelta(deltaScale: Float, panDelta: Offset, centroid: Offset?) {
                val newScale = (internalScale * deltaScale).coerceIn(minScale, maxScale)
                internalScale = newScale
                internalOffset += panDelta
            }

            override fun reset() {
                internalScale = config.initialScale
                internalOffset = Offset.Zero
            }
        }
    }
}
