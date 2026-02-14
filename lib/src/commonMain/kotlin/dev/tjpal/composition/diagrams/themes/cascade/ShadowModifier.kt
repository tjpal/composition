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

fun Modifier.defaultInnerInsetShapeShadow(cornerRadius: Dp = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.DefaultCornerRadius) = this.insetShapeShadow(
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.HighlightShadowColor,
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.CastShadowColor,
    blurRadius = 1.0f,
    strokeSize = 2.0f,
    offsetX = 2.dp,
    offsetY = 2.dp,
    shape = RoundedCornerShape(cornerRadius)
)

fun Modifier.defaultCascadeBackground(cornerRadius: Dp = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.DefaultCornerRadius) =
    this.background(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.BackgroundColor,RoundedCornerShape(cornerRadius))

fun Modifier.defaultCascadeBackground(shape: Shape) =
    this.background(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.BackgroundColor,shape)

fun Modifier.defaultCascadeShapeShadow(cornerRadius: Dp = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.DefaultCornerRadius) = this.shapeShadow(
    highlightShadowColor = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.HighlightShadowColor,
    castShadowColor = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.CastShadowColor,
    blurRadius = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.OutsetShadowBlurRadius,
    offsetX = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.OutsetShadowOffsetX,
    offsetY = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.OutsetShadowOffsetY,
    shape = RoundedCornerShape(cornerRadius)
)

fun Modifier.defaultCascadeShapeShadow(shape: Shape) = this.shapeShadow(
    highlightShadowColor = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.HighlightShadowColor,
    castShadowColor = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.CastShadowColor,
    blurRadius = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.OutsetShadowBlurRadius,
    offsetX = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.OutsetShadowOffsetX,
    offsetY = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.OutsetShadowOffsetY,
    shape = shape
)

fun Modifier.defaultInsetShapeShadow(cornerRadius: Dp = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.DefaultCornerRadius) = this.shapeShadow(
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.CastShadowColor,
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.HighlightShadowColor,
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.InsetShadowBlurRadius,
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.InsetShadowPressedOffsetX,
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.InsetShadowPressedOffsetY,
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