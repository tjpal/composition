package dev.tjpal.composition.foundation.functional

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import dev.tjpal.composition.diagrams.themes.tokens.Theme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin

/**
 * A composable drawing a dot that circulates a circle and leaves a fading trail.
 * The trail is implemented by drawing multiple small circles along the circumference
 * with decreasing alpha values. The whole structure rotates in an uniform manner.
 */
@Composable
fun WaitingCircle(modifier: Modifier = Modifier) {
    val tokens = Theme.current.waiting

    val finalSize = tokens.size
    val finalDotSize =tokens.dotSize
    val finalDotColor = tokens.dotColor
    val finalTrailColor = tokens.trailColor
    val finalRotationDuration = tokens.rotationDurationMs
    val finalTrailFraction = tokens.trailFraction.coerceIn(0f, 1f)
    val finalSegments = tokens.segments

    // animate angle 0..360
    val transition = rememberInfiniteTransition()
    val angleProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = finalRotationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = modifier.size(finalSize)) {
        Canvas(modifier = Modifier.size(finalSize)) {
            val canvasMin = min(size.width, size.height)

            // compute radius for orbiting dots
            val dotRadiusPx = finalDotSize.toPx() / 2f
            val orbitRadius = max(0f, (canvasMin / 2f) - dotRadiusPx)
            val center = Offset(size.width / 2f, size.height / 2f)

            // number of segments to draw for the trail
            val numSegments = max(3, finalSegments)

            val trailArcDegrees = 360f * finalTrailFraction

            // Draw trailing dots from back (most faded) to front (brightest) so overlap looks correct.
            for (i in numSegments downTo 1) {
                val fraction = i.toFloat() / numSegments.toFloat() // in (0..1]

                val angleOffset = (fraction * trailArcDegrees)
                val drawAngle = (angleProgress - angleOffset) % 360f

                val angleRad = (drawAngle - 90f) * (PI / 180.0)
                val x = center.x + (cos(angleRad).toFloat() * orbitRadius)
                val y = center.y + (sin(angleRad).toFloat() * orbitRadius)

                val inv = 1f - (fraction)
                val alpha = (inv.toDouble().pow(1.6)).toFloat().coerceIn(0f, 1f)

                drawCircle(
                    color = finalTrailColor.copy(alpha = alpha),
                    radius = dotRadiusPx,
                    center = Offset(x, y)
                )
            }

            // Draw leading dot (brightest)
            val leadAngleRad = (angleProgress - 90f) * (PI / 180.0)
            val leadX = center.x + (cos(leadAngleRad).toFloat() * orbitRadius)
            val leadY = center.y + (sin(leadAngleRad).toFloat() * orbitRadius)

            drawCircle(
                color = finalDotColor,
                radius = dotRadiusPx,
                center = Offset(leadX, leadY)
            )
        }
    }
}
