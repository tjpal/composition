package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import dev.tjpal.foundation.themes.tokens.InputFieldTokens
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun inputConfiguration(): InputFieldTokens {
    return InputFieldTokens(
        modifier = Modifier.
        background(BackgroundColor, shape = RoundedCornerShape(12.dp)).
        insetShapeShadow(
                HighlightShadowColor,
                CastShadowColor,
                1.0f,
                2.0f, 2.dp, 2.dp,
                RoundedCornerShape(12.dp)
            )
    )
}
