package dev.tjpal.foundation.basics.spacing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import dev.tjpal.foundation.themes.tokens.Theme
import kotlin.math.floor

@Composable
fun PointGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    area: Rect,
    content: @Composable () -> Unit
) {
    val pointGridTheme = Theme.current.grid.point
    val radiusDp = pointGridTheme.dotRadius
    val drawColor = pointGridTheme.color
    val drawAlpha = pointGridTheme.alpha
    val tokenModifier = pointGridTheme.modifier

    Box(modifier = modifier.then(tokenModifier)) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawPointGrid(area, spacing, radiusDp, drawColor, drawAlpha)
        }

        content()
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawPointGrid(
    area: Rect?,
    spacingDp: Dp,
    radiusDp: Dp,
    color: androidx.compose.ui.graphics.Color,
    alpha: Float
) {
    val spacingPx = spacingDp.toPx()
    if (spacingPx <= 0f) return
    val radiusPx = radiusDp.toPx().coerceAtLeast(0f)

    val left = area?.left ?: 0f
    val top = area?.top ?: 0f
    val right = area?.right ?: size.width
    val bottom = area?.bottom ?: size.height

    val areaWidth = right - left
    val areaHeight = bottom - top
    if (areaWidth <= 0f || areaHeight <= 0f) return

    val startX = (floor(left / spacingPx) * spacingPx)
    val startY = (floor(top / spacingPx) * spacingPx)

    var x = startX
    while (x <= right) {
        var y = startY
        while (y <= bottom) {
            val localX = x - left
            val localY = y - top

            drawCircle(color = color.copy(alpha = alpha), radius = radiusPx, center = Offset(localX, localY), style = Fill)

            y += spacingPx
        }
        x += spacingPx
    }
}
