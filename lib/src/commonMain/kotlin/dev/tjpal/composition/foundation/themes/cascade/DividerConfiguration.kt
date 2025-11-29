package dev.tjpal.composition.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import dev.tjpal.composition.foundation.themes.tokens.DividerTheme
import dev.tjpal.composition.foundation.themes.tokens.DividerTokens
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun createDividerConfiguration(): DividerTokens {
    return DividerTokens(
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