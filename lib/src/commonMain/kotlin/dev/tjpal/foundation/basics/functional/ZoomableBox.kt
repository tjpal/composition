package dev.tjpal.foundation.basics.functional

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import dev.tjpal.foundation.utilities.zoom.InitialScaleMode
import dev.tjpal.foundation.utilities.zoom.ZoomableState
import dev.tjpal.foundation.utilities.zoom.rememberZoomableState
import dev.tjpal.foundation.utilities.zoom.zoomableGestures
import kotlin.math.min

@Composable
fun ZoomableBox(
    modifier: Modifier = Modifier,
    state: ZoomableState = rememberZoomableState(),
    initialScaleMode: InitialScaleMode = InitialScaleMode.DEFAULT,
    constraintMeasurementScale: Int = 10,
    content: @Composable () -> Unit
) {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var measuredContentSize by remember { mutableStateOf(IntSize.Zero) }
    var fitScaleSet by remember { mutableStateOf(false) }

    if(initialScaleMode == InitialScaleMode.FIT && !fitScaleSet) {
        if (measuredContentSize.width > 0 && measuredContentSize.height > 0 && containerSize.width > 0 && containerSize.height > 0) {
            val sx = containerSize.width.toFloat() / measuredContentSize.width.toFloat()
            val sy = containerSize.height.toFloat() / measuredContentSize.height.toFloat()

            state.snapTo(min(sx, sy), Offset.Zero)
            fitScaleSet = true
        }
    }

    SubcomposeLayout(modifier = modifier.clipToBounds().then(Modifier.zoomableGestures(state))) { constraints ->
        val containerWidth = constraints.maxWidth
        val containerHeight = constraints.maxHeight
        containerSize = IntSize(containerWidth, containerHeight)

        val measurables = subcompose("content-measure", content)
        val looseConstraints = Constraints(
            0,
            containerWidth * constraintMeasurementScale,
            0,
            containerHeight * constraintMeasurementScale
        )

        val contentItemSizes = measurables.map { it.measure(looseConstraints) }
        if (contentItemSizes.isNotEmpty()) {
            val maxContentWidth = contentItemSizes.maxOf { it.width }
            val maxContentHeight = contentItemSizes.maxOf { it.height }
            measuredContentSize = IntSize(maxContentWidth, maxContentHeight)
        }

        val visibleItems = subcompose("content-visible") {
            Box(modifier = Modifier
                .graphicsLayer {
                    scaleX = state.scale
                    scaleY = state.scale
                    translationX = state.offset.x
                    translationY = state.offset.y
                }
            ) {
                content()
            }
        }

        val visiblePlaceable = visibleItems.map { it.measure(constraints) }

        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        layout(layoutWidth, layoutHeight) {
            visiblePlaceable.forEach { it.placeRelative(0, 0) }
        }
    }
}
