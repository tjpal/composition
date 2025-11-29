package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import dev.tjpal.composition.foundation.themes.tokens.InputFieldTokens
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun inputConfiguration(): InputFieldTokens {
    return InputFieldTokens(
        modifier = Modifier.
        background(BackgroundColor, shape = RoundedCornerShape(12.dp)).
        defaultInnerInsetShapeShadow(12.dp)
    )
}
