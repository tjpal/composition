package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeBackground
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeShapeShadow
import dev.tjpal.composition.diagrams.themes.tokens.FloatingBarTokens

fun createFloatingBarConfiguration(): FloatingBarTokens {
    return FloatingBarTokens(
        modifier = Modifier.Companion
            .defaultCascadeShapeShadow()
            .defaultCascadeBackground()
            .clip(RoundedCornerShape(10.dp)),
        buttonPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        dividerHorizontalPadding = 10.dp,
        dividerVerticalPadding = 6.dp,
        dividerThickness = 1.dp,
        cornerRadius = 10.dp
    )
}
