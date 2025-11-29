package dev.tjpal.composition.foundation.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.tjpal.composition.foundation.themes.tokens.FloatingBarLocation


@Composable
fun FloatingBarTemplate(
    modifier: Modifier = Modifier,
    location: FloatingBarLocation,
    bandThickness: Dp,
    barInset: Dp,
    floatingBar: @Composable BoxScope.() -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Base content fills the entire area
        content()

        // Overlay floating bar in the specified location
        var modifier = when(location) {
            FloatingBarLocation.TOP, FloatingBarLocation.BOTTOM -> Modifier
                .fillMaxWidth()
                .height(bandThickness)
            FloatingBarLocation.LEFT, FloatingBarLocation.RIGHT -> Modifier
                .fillMaxHeight()
                .width(bandThickness)
        }

        modifier = when(location) {
            FloatingBarLocation.TOP -> modifier.padding(top = barInset).align(Alignment.TopCenter)
            FloatingBarLocation.BOTTOM -> modifier.padding(bottom = barInset).align(Alignment.BottomCenter)
            FloatingBarLocation.LEFT -> modifier.padding(start = barInset).align(Alignment.CenterStart)
            FloatingBarLocation.RIGHT -> modifier.padding(end = barInset).align(Alignment.CenterEnd)
        }

        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            floatingBar()
        }
    }
}
