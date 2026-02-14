package dev.tjpal.composition.diagrams.scatter.internal

import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.scatter.models.DiagramRange
import dev.tjpal.composition.diagrams.scatter.utilities.formatTick
import dev.tjpal.composition.diagrams.scatter.utilities.mapToPixel
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.TextType

@Composable
internal fun ScatterTickLabels(
    range: DiagramRange,
    containerSize: Size,
    gridStepsX: Int,
    gridStepsY: Int
) {
    if (containerSize.width <= 0f || containerSize.height <= 0f) {
        return
    }

    val density = LocalDensity.current

    val ticksX = (0..gridStepsX).map { i ->
        val value = range.minX + (i.toFloat() / gridStepsX) * (range.maxX - range.minX)
        value
    }

    val ticksY = (0..gridStepsY).map { i ->
        val value = range.minY + (i.toFloat() / gridStepsY) * (range.maxY - range.minY)
        value
    }

    ticksX.forEach { value ->
        val position = mapToPixel(range, containerSize, 0f, 0f, value, range.minY)

        Text(
            text = formatTick(value),
            type = TextType.DEFAULT,
            modifier = Modifier.offset(
                with(density) { position.x.toDp() } - 12.dp,
                with(density) { position.y.toDp() } + 4.dp
            )
        )
    }

    ticksY.forEach { value ->
        val position = mapToPixel(range, containerSize, 0f, 0f, range.minX, value)

        Text(
            text = formatTick(value),
            type = TextType.DEFAULT,
            modifier = Modifier.offset(
                with(density) { position.x.toDp() } - 44.dp,
                with(density) { position.y.toDp() } - 6.dp
            )
        )
    }
}
