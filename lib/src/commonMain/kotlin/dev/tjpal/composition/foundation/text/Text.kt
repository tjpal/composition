package dev.tjpal.composition.foundation.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import dev.tjpal.composition.diagrams.themes.tokens.TextType
import dev.tjpal.composition.diagrams.themes.tokens.Theme
import dev.tjpal.composition.diagrams.themes.tokens.Typography

@Composable
fun Text(
    text: String,
    type: TextType = TextType.DEFAULT,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        text = text,
        typography = Theme.current.typography.getTypography(
            type
        ),
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow,
        softWrap = softWrap,
        onTextLayout = onTextLayout
    )
}

@Composable
fun Text(
    text: String,
    typography: Typography,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    BasicText(
        text = text,
        style = typography.style,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow,
        softWrap = softWrap,
        onTextLayout = onTextLayout
    )
}
