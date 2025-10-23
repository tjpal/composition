package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.ButtonStateTokens
import dev.tjpal.foundation.themes.tokens.ButtonCategoryTokens
import dev.tjpal.foundation.themes.tokens.ButtonTokens

private val ButtonPadding = PaddingValues(24.dp, 10.dp, 24.dp, 10.dp)

fun buttonConfiguration(): ButtonTokens {
    return ButtonTokens(
        primaryButton = ButtonCategoryTokens(
            default = ButtonStateTokens(
                modifier = Modifier.defaultCascadeShapeShadow().defaultCascadeBackground().padding(ButtonPadding)
            ),
            pressed = ButtonStateTokens(
                modifier = Modifier.defaultInsetShapeShadow().defaultCascadeBackground().padding(ButtonPadding)
            )
        ),
        // A shy button use flat design with no shadow until the cursor is moved over it. The goal is to reduce visual noise.
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
        )
    )
}
