package dev.tjpal.foundation.basics.spacing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.DividerType
import dev.tjpal.foundation.themes.tokens.Theme

@Composable
fun VerticalDivider(
    type: DividerType = DividerType.SECONDARY,
    horizontalPadding: Dp = 0.dp,
    verticalPadding: Dp = 0.dp,
    thickness: Dp = 1.dp
) {
    Box(
        modifier = Modifier
            .padding(start = horizontalPadding, end = horizontalPadding, top = verticalPadding, bottom = verticalPadding)
            .fillMaxHeight()
            .width(thickness)
            .then(Theme.current.divider.getTheme(type).modifier)
    )
}
