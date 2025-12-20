package dev.tjpal.composition.diagrams.scatter.models

import androidx.compose.ui.graphics.Color

data class DiagramRange(
    val minX: Float,
    val maxX: Float,
    val minY: Float,
    val maxY: Float
)

data class ScatterEntry<T>(
    val id: String,
    val x: Float,
    val y: Float,
    val label: String? = null,
    val color: Color,
    val data: T? = null
)
