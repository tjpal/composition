package dev.tjpal.composition.foundation.basics.spacing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import dev.tjpal.composition.foundation.themes.tokens.Theme
import kotlin.math.floor

@Composable
fun LineGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    area: Rect,
    content: @Composable () -> Unit
) {
    val gridLineTheme = Theme.current.grid.line
    val strokeDp: Dp = gridLineTheme.strokeWidth
    val drawColor = gridLineTheme.color
    val drawAlpha = gridLineTheme.alpha
    val tokenModifier = gridLineTheme.modifier

    Box(modifier = modifier.then(tokenModifier)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawLineGrid(area, spacing, strokeDp, drawColor, drawAlpha)
        }
        content()
    }
}

private fun DrawScope.drawLineGrid(
    area: Rect?,
    spacingDp: Dp,
    strokeDp: Dp,
    color: Color,
    alpha: Float
) {
    val spacingPx = spacingDp.toPx()
    if (spacingPx <= 0f) return
    val strokePx = strokeDp.toPx().coerceAtLeast(0f)

    val left = area?.left ?: 0f
    val top = area?.top ?: 0f
    val right = area?.right ?: size.width
    val bottom = area?.bottom ?: size.height

    val areaWidth = right - left
    val areaHeight = bottom - top
    if (areaWidth <= 0f || areaHeight <= 0f) return

    val stroke = Stroke(width = strokePx, cap = StrokeCap.Square)

    val startX = (floor(left / spacingPx) * spacingPx)
    val startY = (floor(top / spacingPx) * spacingPx)

    var x = startX
    while (x <= right) {
        val localX = x - left

        drawLine(color = color.copy(alpha = alpha), start = Offset(localX, 0f), end = Offset(localX, areaHeight), strokeWidth = stroke.width)

        x += spacingPx
    }

    var y = startY
    while (y <= bottom) {
        val localY = y - top

        drawLine(color = color.copy(alpha = alpha), start = Offset(0f, localY), end = Offset(areaWidth, localY), strokeWidth = stroke.width)

        y += spacingPx
    }
}
