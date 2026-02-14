package dev.tjpal.composition.structure.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tjpal.composition.diagrams.themes.tokens.Theme

@Composable
fun FullCardFrame(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    CardFrame(modifier, 4f, 2f, content)
}

@Composable
fun VerticalHalfFrame(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    CardFrame(modifier, 4f, 1f, content)
}

@Composable
fun CardFrame(modifier: Modifier = Modifier, scaleWidth: Float, scaleHeight: Float, content: @Composable () -> Unit) {
    val theme = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.card
    Box(
        modifier = Modifier.
            width(theme.basicConfiguration.cardTileSize * scaleWidth).
            height(theme.basicConfiguration.cardTileSize * scaleHeight).
            then(theme.basicConfiguration.modifier).
            then(modifier)
    ) {
        content()
    }
}