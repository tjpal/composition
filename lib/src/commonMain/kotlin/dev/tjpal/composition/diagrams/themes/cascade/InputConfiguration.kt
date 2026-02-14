package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import dev.tjpal.composition.diagrams.themes.tokens.InputFieldTokens
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import dev.tjpal.composition.diagrams.themes.cascade.defaultInnerInsetShapeShadow
import dev.tjpal.composition.diagrams.themes.tokens.TextType

fun inputConfiguration(): dev.tjpal.composition.diagrams.themes.tokens.InputFieldTokens {
    return _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.InputFieldTokens(
        modifier = _root_ide_package_.androidx.compose.ui.Modifier.Companion.background(
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.BackgroundColor,
            shape = RoundedCornerShape(6.dp)
        ).defaultInnerInsetShapeShadow(6.dp),
        typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.typographyConfiguration()
            .getTypography(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
        rowHeightFallback = 18.dp
    )
}
