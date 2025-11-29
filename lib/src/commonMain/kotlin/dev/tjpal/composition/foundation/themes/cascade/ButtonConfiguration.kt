package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.themes.tokens.ButtonStateTokens
import dev.tjpal.composition.foundation.themes.tokens.ButtonCategoryTokens
import dev.tjpal.composition.foundation.themes.tokens.ButtonTokens

private val ButtonPadding = PaddingValues(24.dp, 10.dp, 24.dp, 10.dp)
private val IconButtonSize = 48.dp

fun buttonConfiguration(): ButtonTokens {
    return ButtonTokens(
        primaryButton = ButtonCategoryTokens(
            default = ButtonStateTokens(
                modifier = Modifier.Companion.defaultCascadeShapeShadow().defaultCascadeBackground().padding(ButtonPadding)
            ),
            pressed = ButtonStateTokens(
                modifier = Modifier.Companion.defaultInsetShapeShadow().defaultCascadeBackground().padding(ButtonPadding)
            )
        ),
        // A shy button uses flat design with no shadow until the cursor is moved over it. The goal is to reduce visual noise.
        shyButton = ButtonCategoryTokens(
            flat = ButtonStateTokens(
                modifier = Modifier.defaultCascadeBackground().padding(ButtonPadding)
            ),
            default = ButtonStateTokens(
                modifier = Modifier.defaultCascadeShapeShadow().defaultCascadeBackground().padding(ButtonPadding)
            ),
            pressed = ButtonStateTokens(
                modifier = Modifier.defaultInsetShapeShadow().defaultCascadeBackground().padding(ButtonPadding)
            )
        ),
        // Icon buttons are visually compact and provide a small visual size.
        iconButton = ButtonCategoryTokens(
            flat = ButtonStateTokens(
                modifier = Modifier.defaultCascadeBackground().size(IconButtonSize)
            ),
            default = ButtonStateTokens(
                modifier = Modifier.defaultCascadeShapeShadow().defaultCascadeBackground().size(IconButtonSize)
            ),
            pressed = ButtonStateTokens(
                modifier = Modifier.defaultInsetShapeShadow().defaultCascadeBackground().size(IconButtonSize)
            )
        )
    )
}
