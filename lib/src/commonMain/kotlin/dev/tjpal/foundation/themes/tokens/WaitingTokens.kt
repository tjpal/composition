package dev.tjpal.foundation.themes.tokens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Tokens describing the Waiting animation defaults.
 * Add any additional parameters you want to expose to the theming system here.
 */
data class WaitingTokens(
    val size: Dp = 56.dp,
    val dotSize: Dp = 8.dp,
    val dotColor: Color = Color(0xFF0066FF),
    val trailColor: Color = Color(0xFF0066FF),
    val rotationDurationMs: Int = 1200,
    /** fraction of the full circle used as trail (0..1) */
    val trailFraction: Float = 0.55f,
    /** number of segments used to paint the trail. More segments = smoother fade. */
    val segments: Int = 24
)
