package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.ButtonStateTheme
import dev.tjpal.foundation.themes.tokens.ButtonTheme
import dev.tjpal.foundation.themes.tokens.ButtonThemes

private val ButtonCornerRadius = 6f.dp
private val ButtonPadding = PaddingValues(24.dp, 10.dp, 24.dp, 10.dp)

fun buttonConfiguration(): ButtonThemes {
    return ButtonThemes(

        primaryButton = ButtonTheme(
            default = ButtonStateTheme(
                modifier = Modifier.
                shapeShadow(
                    highlightShadowColor = HighlightShadowColor,
                    castShadowColor = CastShadowColor,
                    blurRadius = OutsetShadowBlurRadius,
                    offsetX = OutsetShadowOffsetX,
                    offsetY = OutsetShadowOffsetY,
                    shape = RoundedCornerShape(ButtonCornerRadius)
                ).
                background(
                    BackgroundColor,
                    RoundedCornerShape(ButtonCornerRadius)
                ).
                padding(ButtonPadding)
            ),
            pressed = ButtonStateTheme(
                modifier = Modifier.
                shapeShadow(
                    CastShadowColor,
                    HighlightShadowColor,
                    InsetShadowBlurRadius,
                    InsetShadowPressedOffsetX,
                    InsetShadowPressedOffsetY,
                    RoundedCornerShape(ButtonCornerRadius)
                ).
                background(
                    BackgroundColor,
                    RoundedCornerShape(ButtonCornerRadius)
                ).
                padding(ButtonPadding)
            )
        )
    )
}