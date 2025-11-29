package dev.tjpal.composition.foundation.templates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.tjpal.composition.foundation.basics.functional.WaitingCircle
import dev.tjpal.composition.foundation.basics.text.Text
import dev.tjpal.composition.foundation.themes.tokens.TextType
import dev.tjpal.composition.foundation.themes.tokens.Theme

/**
 * Template which centers a waiting animation above the vertical center axis and
 * a text below that axis. The template by default fills the available width and height
 * (Modifier.fillMaxSize()).
 */
@Composable
fun WaitingTemplate(
    modifier: Modifier = Modifier,
    text: String,
) {
    val tokens = Theme.current.waiting
    val waitingSize = tokens.size
    val gap = tokens.waitingTemplateElementGap

    Box(modifier = modifier.fillMaxSize().then(modifier)) {
        WaitingCircle(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = -(waitingSize / 2) - (gap / 2))
        )

        Text(
            text = text,
            type = TextType.PRIMARY,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (waitingSize / 2) + (gap / 2))
        )
    }
}
