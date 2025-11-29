package dev.tjpal.composition.foundation.basics.functional

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import dev.tjpal.composition.foundation.themes.tokens.ButtonType
import dev.tjpal.composition.foundation.themes.tokens.Theme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    type: ButtonType = ButtonType.DEFAULT,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isHovered by remember { mutableStateOf(false) }

    val buttonTheme = Theme.current.button.getButtonTheme(type)

    // Shy buttons do not render any border until hovered. They are used to reduce visual noise when other
    // separating visual elements are sufficient.
    val buttonModifier = when (type) {
        ButtonType.SHY -> {
            when {
                isPressed -> buttonTheme.pressed.modifier
                isHovered -> buttonTheme.default.modifier
                else -> buttonTheme.flat.modifier
            }
        }
        else -> if (isPressed) buttonTheme.pressed.modifier else buttonTheme.default.modifier
    }

    Box(
        modifier = modifier.
            onPointerEvent(PointerEventType.Enter) {
                isHovered = true
            }.
            onPointerEvent(PointerEventType.Exit) {
                isHovered = false
            }.
            clickable(interactionSource = interactionSource, indication = null, onClick = onClick).
            then(buttonModifier)
    ) {
        content()
    }
}
