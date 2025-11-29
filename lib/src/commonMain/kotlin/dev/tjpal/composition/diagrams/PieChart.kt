package dev.tjpal.composition.diagrams

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PieChart(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Canvas(modifier = modifier) {
            drawCircle(Color.Red, 50.0f, center)
        }
    }
}