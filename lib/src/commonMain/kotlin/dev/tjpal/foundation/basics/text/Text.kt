package dev.tjpal.foundation.basics.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tjpal.foundation.themes.tokens.TextType
import dev.tjpal.foundation.themes.tokens.Theme
import dev.tjpal.foundation.themes.tokens.Typography

@Composable
fun Text(
    text: String,
    type: TextType = TextType.DEFAULT,
    modifier: Modifier = Modifier
) {
    Text(text, Theme.current.typography.getTypography(type), modifier)
}

@Composable
fun Text(
    text: String,
    typography: Typography,
    modifier: Modifier = Modifier
) {
    BasicText(
        text = text,
        style = typography.style,
        modifier = modifier
    )
}