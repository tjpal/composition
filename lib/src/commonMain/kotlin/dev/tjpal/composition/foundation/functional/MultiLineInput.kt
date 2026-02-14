package dev.tjpal.composition.foundation.functional

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.TextType
import dev.tjpal.composition.diagrams.themes.tokens.Theme
import dev.tjpal.composition.foundation.functional.isSpecified
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.Density

/**
 * onValueChanged must be provided to receive updates when the text changes. Update the value accordingly.
 */
@Composable
fun MultiLineInput(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String? = null,
    numVisibleLines: Int = 4,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    require(numVisibleLines >= 1) { "maxLines must be >= 1" }

    val tokens = Theme.current.inputField
    val typography = tokens.typography
    val contentPadding = tokens.contentPadding

    val density = LocalDensity.current

    val desiredHeightDp = calculateDesiredHeightDp(
        numVisibleLines,
        typography.style.lineHeight,
        tokens.rowHeightFallback,
        contentPadding,
        density
    )

    val scrollState = rememberScrollState()
    var containerHeightPx by remember { mutableStateOf(0f) }
    var isFocused by remember { mutableStateOf(false) }
    var lastLayout by remember { mutableStateOf<TextLayoutResult?>(null) }

    Box(
        modifier = tokens.modifier.then(modifier)
            .height(desiredHeightDp)
            .padding(contentPadding)
            .clipToBounds()
            .onGloballyPositioned { coordinates -> containerHeightPx = coordinates.size.height.toFloat() }
            .verticalScroll(scrollState),
        contentAlignment = Alignment.TopStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            textStyle = typography.style,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().onFocusChanged { focusState -> isFocused = focusState.isFocused },
            onTextLayout = { textLayout -> lastLayout = textLayout }
        ) { innerTextField ->
            if (value.text.isEmpty() && !placeholder.isNullOrEmpty() && !isFocused) {
                Text(
                    text = placeholder,
                    type = TextType.PLACEHOLDER
                )
            }

            innerTextField()
        }
    }

    LaunchedEffect(lastLayout, value.selection, value.text, scrollState.maxValue, containerHeightPx) {
        val textLayout = lastLayout ?: return@LaunchedEffect
        ensureCaretVisible(
            scrollState,
            textLayout,
            value,
            containerHeightPx,
            contentPadding,
            density
        )
    }
}

/**
 * Pass value as null to use uncontrolled mode (internal state). Otherwise you must react to onValueChange to update the value.
 */
@Composable
fun MultiLineInput(
    modifier: Modifier = Modifier,
    value: String? = null,
    onValueChange: (String) -> Unit = {},
    placeholder: String? = null,
    numVisibleLines: Int = 4,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val textFieldValueState = remember { mutableStateOf(TextFieldValue(value ?: "")) }

    LaunchedEffect(value) {

        if (value != null && value != textFieldValueState.value.text) {
            val prevSelection = textFieldValueState.value.selection
            val selStart = prevSelection.start.coerceIn(0, value.length)
            val selEnd = prevSelection.end.coerceIn(0, value.length)
            textFieldValueState.value = TextFieldValue(text = value, selection = TextRange(selStart, selEnd), composition = null)
        }
    }

    val onTextFieldValueChanged: (TextFieldValue) -> Unit = { newValue ->
        textFieldValueState.value = newValue
        onValueChange(newValue.text)
    }

    MultiLineInput(
        modifier = modifier,
        value = textFieldValueState.value,
        onValueChange = onTextFieldValueChanged,
        placeholder = placeholder,
        numVisibleLines = numVisibleLines,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

private fun calculateDesiredHeightDp(
    maxLines: Int,
    lineHeight: TextUnit,
    fallbackLineHeightDp: Dp,
    contentPadding: PaddingValues,
    density: Density
): Dp {
    val lineHeightPx = with(density) {
        if (lineHeight.isSpecified()) lineHeight.toPx() else fallbackLineHeightDp.toPx()
    }

    val paddingTopPx = with(density) { contentPadding.calculateTopPadding().toPx() }
    val paddingBottomPx = with(density) { contentPadding.calculateBottomPadding().toPx() }

    val desiredPx = lineHeightPx * maxLines + paddingTopPx + paddingBottomPx

    return with(density) { desiredPx.toDp() }
}

private suspend fun ensureCaretVisible(
    scrollState: ScrollState,
    layout: TextLayoutResult,
    value: TextFieldValue,
    containerHeightPx: Float,
    contentPadding: PaddingValues,
    density: Density
) {
    val caretOffset = value.selection.end.coerceIn(0, value.text.length)

    val cursorRect: Rect = try { layout.getCursorRect(caretOffset) } catch (_: Throwable) { return }

    val paddingTop = with(density) { contentPadding.calculateTopPadding().toPx() }
    val cursorTopInScroll = paddingTop + cursorRect.top
    val cursorBottomInScroll = paddingTop + cursorRect.bottom

    val visibleTop = scrollState.value.toFloat()
    val visibleBottom = visibleTop + containerHeightPx

    val margin = 8f
    var target = scrollState.value

    if (cursorBottomInScroll + margin > visibleBottom) {
        target = (cursorBottomInScroll + margin - containerHeightPx).toInt()
    } else if (cursorTopInScroll - margin < visibleTop) {
        target = (cursorTopInScroll - margin).toInt()
    }

    target = target.coerceIn(0, scrollState.maxValue)
    if (target != scrollState.value) {
        scrollState.animateScrollTo(target)
    }
}

private fun TextUnit.isSpecified(): Boolean {
    return try {
        !this.value.isNaN() && this.value > 0f
    } catch (e: Exception) {
        false
    }
}
