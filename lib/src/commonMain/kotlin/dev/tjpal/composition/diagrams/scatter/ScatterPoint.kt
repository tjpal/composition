package dev.tjpal.composition.diagrams.scatter

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import dev.tjpal.composition.diagrams.scatter.models.ScatterEntry
import dev.tjpal.composition.foundation.themes.tokens.ScatterTokens

@Composable
fun <T> ScatterPoint(
    entry: ScatterEntry<T>,
    tokens: ScatterTokens,
    onClick: (ScatterEntry<T>) -> Unit = {},
    onHoverChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val borderPx = with(density) { tokens.pointBorderWidth.toPx() }

    Box(
        modifier = modifier
            .size(tokens.pointRadius * 2)
            .pointerInput(entry.id) {
                awaitPointerEventScope {
                    var wasHovered = false

                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> {
                                if (!wasHovered) {
                                    wasHovered = true
                                    onHoverChanged(true)
                                }
                            }
                            PointerEventType.Exit -> {
                                if (wasHovered) {
                                    wasHovered = false
                                    onHoverChanged(false)
                                }
                            }
                            PointerEventType.Move -> {
                                if (!wasHovered) {
                                    wasHovered = true
                                    onHoverChanged(true)
                                }
                            }
                            else -> { /* no-op */ }
                        }
                    }
                }
            }
            .pointerInput(entry.id) {
                detectTapGestures(onTap = { onClick(entry) })
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(tokens.pointRadius * 2)
                .clip(CircleShape)
                .background(entry.color)
                .drawBehind {
                    drawCircle(
                        color = tokens.pointBorderColor,
                        radius = size.minDimension / 2f,
                        style = Stroke(width = borderPx)
                    )
                }
        )
    }
}
