package dev.tjpal.composition.foundation.functional

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.TextUnit
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.TextType
import dev.tjpal.composition.diagrams.themes.tokens.Theme

/**
 * Pass value as null to use uncontrolled mode (internal state). Otherwise you must react to onValueChange to update the value.
 */
@Composable
fun Input(
    modifier: Modifier = Modifier,
    value: String? = null,
    onValueChange: (String) -> Unit = {},
    placeholder: String? = null,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val tokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.inputField
    val typography = tokens.typography
    val contentPadding = tokens.contentPadding

    // The client code can choose to control the value externally (controlled mode)
    // or let this Input manage its own internal state (uncontrolled mode)
    val internalState = remember { mutableStateOf(value ?: "") }
    LaunchedEffect(value) {
        if (value != null) {
            internalState.value = value
        }
    }

    val effectiveValue = value ?: internalState.value
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = tokens.modifier.then(modifier).padding(contentPadding).clipToBounds(),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = effectiveValue,
            onValueChange = { new ->
                if (value == null) {
                    internalState.value = new
                }
                onValueChange(new)
            },
            singleLine = true,
            maxLines = maxLines,
            textStyle = typography.style,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> isFocused = focusState.isFocused }
        ) { innerTextField ->
            // Show the placeholder only when there is no text and the field is not focused.
            if (effectiveValue.isEmpty() && !placeholder.isNullOrEmpty() && !isFocused) {
                _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                    text = placeholder,
                    type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PLACEHOLDER
                )
            }
            innerTextField()
        }
    }
}

private fun TextUnit.isSpecified(): Boolean {
    return try {
        !this.value.isNaN() && this.value > 0f
    } catch (e: Exception) {
        false
    }
}
