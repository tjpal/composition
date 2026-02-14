package dev.tjpal.composition.diagrams.scatter.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import dev.tjpal.composition.diagrams.scatter.models.DiagramRange
import dev.tjpal.composition.diagrams.scatter.models.ScatterEntry
import dev.tjpal.composition.diagrams.scatter.utilities.mapToPixel
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.ScatterTokens
import dev.tjpal.composition.diagrams.themes.tokens.TextType

@Composable
internal fun <T> ScatterTooltipOverlay(
    highlighted: ScatterEntry<T>,
    range: DiagramRange,
    containerSize: Size,
    tokens: ScatterTokens
) {
    if (containerSize.width <= 0f || containerSize.height <= 0f) {
        return
    }

    val density = LocalDensity.current
    val position = mapToPixel(range, containerSize, 0f, 0f, highlighted.x, highlighted.y)

    Box(modifier = Modifier.offset(
        with(density) { (position.x + tokens.tooltipShiftX).toDp() },
        with(density) { (position.y - tokens.tooltipShiftY).toDp() })
    ) {
        Box(
            modifier = tokens.tooltipShapeModifier
        ) {
            Text(
                text = highlighted.label ?: "-",
                type = TextType.DEFAULT
            )
        }
    }
}
