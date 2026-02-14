package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens

fun createFunnelConfiguration(): dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens {
    return _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens(
        background = _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.BackgroundColor,
        stageSeparatorColor = Color(102, 116, 141, 255),
        stageSeparatorStrokeWidth = 2.dp,
        innerSeparatorColor = Color(198, 206, 220, 255),
        innerSeparatorStrokeWidth = 1.dp,
        tooltipShapeModifier = Modifier.background(Color.White, shape = RoundedCornerShape(6.dp)).padding(6.dp),
        itemSpacing = 4.dp,
        itemMinDiameter = 6.dp,
        itemDefaultCellPadding = 4.dp
    )
}
