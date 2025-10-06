package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import dev.tjpal.foundation.themes.common.DividerTheme
import dev.tjpal.foundation.themes.common.DividerThemes
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun createDividerConfiguration(): DividerThemes {
    return DividerThemes(
        primary = DividerTheme(
            modifier = Modifier
                .height(1.dp)
                .background(Color(150, 150, 150, 255))
        ),
        secondary = DividerTheme(
            modifier = Modifier
                .height(1.dp)
                .background(Color(220, 220, 220, 255))
        )
    )
}