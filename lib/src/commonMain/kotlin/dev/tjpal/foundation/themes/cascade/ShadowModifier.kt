package dev.tjpal.foundation.themes.cascade

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter

fun Outline.outlineShadow(canvas: Canvas, paint: Paint, color: Color, offsetX: Float, offsetY: Float): Outline {
    paint.color = color

    canvas.save()
    canvas.translate(offsetX, offsetY)
    canvas.drawOutline(this, paint)
    canvas.restore()

    return this
}

fun Modifier.insetShapeShadow(
    highlightShadowColor: Color,
    castShadowColor: Color,
    blurRadius: Float,
    strokeSize: Float,
    offsetX: Dp,
    offsetY: Dp,
    shape: Shape
) = clip(shape).then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint().also {
                it.isAntiAlias = true
                it.style = PaintingStyle.Stroke
                it.strokeWidth = strokeSize
            }

            if (blurRadius != 0f) {
                paint.asFrameworkPaint().maskFilter =
                    MaskFilter.makeBlur(FilterBlurMode.NORMAL, blurRadius, true)
            }

            shape.
            createOutline(size, layoutDirection, this).
            outlineShadow(canvas, paint, castShadowColor, offsetX.toPx(), offsetY.toPx()).
            outlineShadow(canvas, paint, highlightShadowColor, (-offsetX).toPx(), (-offsetY).toPx())
        }
    }

)

fun Modifier.shapeShadow(
    highlightShadowColor: Color,
    castShadowColor: Color,
    blurRadius: Float,
    offsetX: Dp,
    offsetY: Dp,
    shape: Shape
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint().also {
                it.isAntiAlias = true
            }

            if (blurRadius != 0f) {
                paint.asFrameworkPaint().maskFilter =
                    MaskFilter.makeBlur(FilterBlurMode.NORMAL, blurRadius, true)
            }

            shape.
            createOutline(size, layoutDirection, this).
            outlineShadow(canvas, paint, castShadowColor, offsetX.toPx(), offsetY.toPx()).
            outlineShadow(canvas, paint, highlightShadowColor, (-offsetX).toPx(), (-offsetY).toPx())
        }
    }
)