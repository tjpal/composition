package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.cascade.insetShapeShadow
import dev.tjpal.composition.diagrams.themes.cascade.outlineShadow
import dev.tjpal.composition.diagrams.themes.cascade.shapeShadow
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

fun Modifier.defaultInnerInsetShapeShadow(cornerRadius: Dp = DefaultCornerRadius) = this.insetShapeShadow(
    HighlightShadowColor,
    CastShadowColor,
    blurRadius = 1.0f,
    strokeSize = 2.0f,
    offsetX = 2.dp,
    offsetY = 2.dp,
    shape = RoundedCornerShape(cornerRadius)
)

fun Modifier.defaultCascadeBackground(cornerRadius: Dp = DefaultCornerRadius) =
    this.background(BackgroundColor,RoundedCornerShape(cornerRadius))

fun Modifier.defaultCascadeBackground(shape: Shape) =
    this.background(BackgroundColor,shape)

fun Modifier.defaultCascadeShapeShadow(cornerRadius: Dp = DefaultCornerRadius) = this.shapeShadow(
    highlightShadowColor = HighlightShadowColor,
    castShadowColor = CastShadowColor,
    blurRadius = OutsetShadowBlurRadius,
    offsetX = OutsetShadowOffsetX,
    offsetY = OutsetShadowOffsetY,
    shape = RoundedCornerShape(cornerRadius)
)

fun Modifier.defaultCascadeShapeShadow(shape: Shape) = this.shapeShadow(
    highlightShadowColor = HighlightShadowColor,
    castShadowColor = CastShadowColor,
    blurRadius = OutsetShadowBlurRadius,
    offsetX = OutsetShadowOffsetX,
    offsetY = OutsetShadowOffsetY,
    shape = shape
)

fun Modifier.defaultInsetShapeShadow(cornerRadius: Dp = DefaultCornerRadius) = this.shapeShadow(
    CastShadowColor,
    HighlightShadowColor,
    InsetShadowBlurRadius,
    InsetShadowPressedOffsetX,
    InsetShadowPressedOffsetY,
    RoundedCornerShape(cornerRadius)
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