package dev.tjpal.composition.foundation.spacing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.tokens.DividerType
import dev.tjpal.composition.diagrams.themes.tokens.Theme

@Composable
fun VerticalDivider(
    type: dev.tjpal.composition.diagrams.themes.tokens.DividerType = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerType.SECONDARY,
    horizontalPadding: Dp = 0.dp,
    verticalPadding: Dp = 0.dp,
    thickness: Dp = 1.dp
) {
    Box(
        modifier = Modifier
            .padding(start = horizontalPadding, end = horizontalPadding, top = verticalPadding, bottom = verticalPadding)
            .fillMaxHeight()
            .width(thickness)
            .then(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.divider.getTheme(type).modifier)
    )
}
