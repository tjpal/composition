package dev.tjpal.composition.diagrams.scatter.utilities

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import dev.tjpal.composition.diagrams.scatter.models.DiagramRange

fun mapToPixel(
    range: DiagramRange,
    canvasSize: Size,
    contentPaddingLeft: Float = 0f,
    contentPaddingTop: Float = 0f,
    x: Float,
    y: Float
): Offset {
    val contentWidth = canvasSize.width
    val contentHeight = canvasSize.height

    // Fraction inside the data range (0..1)
    val fractionX = if (range.maxX - range.minX == 0f) 0.5f else (x - range.minX) / (range.maxX - range.minX)
    val fractionY = if (range.maxY - range.minY == 0f) 0.5f else (y - range.minY) / (range.maxY - range.minY)

    // Compute unclamped pixel positions within the full area including padding
    val pxUnclamped = contentPaddingLeft + (fractionX * contentWidth)
    val pyUnclamped = contentPaddingTop + contentHeight - (fractionY * contentHeight)

    // clamp to the visible area (include padding offsets)
    val px = pxUnclamped.coerceIn(contentPaddingLeft, contentPaddingLeft + contentWidth)
    val py = pyUnclamped.coerceIn(contentPaddingTop, contentPaddingTop + contentHeight)

    return Offset(px, py)
}