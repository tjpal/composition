package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import dev.tjpal.composition.foundation.themes.tokens.InputFieldTokens
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import dev.tjpal.composition.foundation.themes.tokens.TextType

fun inputConfiguration(): InputFieldTokens {
    return InputFieldTokens(
        modifier = Modifier.
        background(BackgroundColor, shape = RoundedCornerShape(6.dp)).
        defaultInnerInsetShapeShadow(6.dp),
        typography = typographyConfiguration().getTypography(TextType.DEFAULT),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
        rowHeightFallback = 18.dp
    )
}
