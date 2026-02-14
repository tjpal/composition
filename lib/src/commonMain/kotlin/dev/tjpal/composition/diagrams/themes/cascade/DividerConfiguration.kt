package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import dev.tjpal.composition.diagrams.themes.tokens.DividerTheme
import dev.tjpal.composition.diagrams.themes.tokens.DividerTokens
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun createDividerConfiguration(): dev.tjpal.composition.diagrams.themes.tokens.DividerTokens {
    return _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerTokens(
        primary = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerTheme(
            modifier = Modifier
                .height(1.dp)
                .background(Color(150, 150, 150, 255))
        ),
        secondary = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerTheme(
            modifier = Modifier
                .height(1.dp)
                .background(Color(220, 220, 220, 255))
        )
    )
}