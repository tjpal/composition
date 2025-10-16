package dev.tjpal.foundation.basics.functional

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.tjpal.foundation.themes.tokens.ButtonType
import dev.tjpal.foundation.themes.tokens.Theme

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    type: ButtonType = ButtonType.DEFAULT,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonTheme = Theme.current.buttons.getButtonTheme(type)
    val buttonModifier = if(isPressed) buttonTheme.pressed.modifier else buttonTheme.default.modifier

    Box(modifier = modifier.
        clickable(interactionSource = interactionSource, indication = null, onClick = onClick).
        then(buttonModifier)
    ) {
        content()
    }
}