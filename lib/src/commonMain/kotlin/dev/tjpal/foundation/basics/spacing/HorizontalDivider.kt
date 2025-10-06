package dev.tjpal.foundation.basics.spacing

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tjpal.foundation.themes.common.DividerType
import dev.tjpal.foundation.themes.common.Theme

@Composable
fun HorizontalDivider(type: DividerType = DividerType.SECONDARY) {
    Box(modifier = Modifier.
        fillMaxWidth().
        then(Theme.current.dividerThemes.getTheme(type).modifier)
    )
}