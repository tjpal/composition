package dev.tjpal.composition.foundation.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.tjpal.composition.diagrams.themes.tokens.Theme

@Composable
fun Link(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Text(
        text,
        Theme.current.typography.link,
        modifier = Modifier.clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .then(modifier)
    )
}