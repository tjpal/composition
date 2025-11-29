package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.themes.tokens.WaitingTokens

/**
 * Cascade theme instantiation for the waiting animation tokens.
 * Cascade is hybrid between Neumorphism and Flat — this animation is visual and typically
 * doesn't require the shape shadow modifiers. If you want a neumorphic feeling, consumers
 * can apply the recommended modifiers to the containing surface.
 */
fun waitingConfiguration(): WaitingTokens {
    return WaitingTokens(
        size = 64.dp,
        dotSize = 10.dp,
        dotColor = Color(0xFF2B6FFF),
        trailColor = Color(0xFF2B6FFF).copy(alpha = 0.9f),
        rotationDurationMs = 1000,
        trailFraction = 0.6f,
        segments = 28
    )
}
