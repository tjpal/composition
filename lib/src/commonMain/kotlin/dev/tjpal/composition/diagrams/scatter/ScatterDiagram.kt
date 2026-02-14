package dev.tjpal.composition.diagrams.scatter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import dev.tjpal.composition.diagrams.scatter.internal.ScatterGridAndAxesCanvas
import dev.tjpal.composition.diagrams.scatter.internal.ScatterPointsOverlay
import dev.tjpal.composition.diagrams.scatter.internal.ScatterTickLabels
import dev.tjpal.composition.diagrams.scatter.internal.ScatterTooltipOverlay
import dev.tjpal.composition.diagrams.scatter.models.DiagramRange
import dev.tjpal.composition.diagrams.scatter.models.ScatterEntry
import dev.tjpal.composition.diagrams.themes.tokens.Theme

@Composable
fun <T> ScatterDiagram(
    modifier: Modifier = Modifier,
    range: DiagramRange,
    entries: List<ScatterEntry<T>>,
    gridStepsX: Int = 4,
    gridStepsY: Int = 4,
    onPointClick: (ScatterEntry<T>) -> Unit = {},
    onPointHover: (ScatterEntry<T>?) -> Unit = {}
) {
    val tokens = Theme.current.scatter

    BoxWithConstraints(modifier = modifier.background(tokens.background).fillMaxSize()) {
        var containerSize = Size(constraints.maxWidth.toFloat(), constraints.maxHeight.toFloat())

        val hoveredId = remember { mutableStateOf<String?>(null) }
        val selectedId = remember { mutableStateOf<String?>(null) }

        ScatterGridAndAxesCanvas(
            modifier = Modifier.fillMaxSize(),
            range = range,
            gridStepsX = gridStepsX,
            gridStepsY = gridStepsY,
            tokens = tokens
        )

        ScatterTickLabels(
            range = range,
            containerSize = containerSize,
            gridStepsX = gridStepsX,
            gridStepsY = gridStepsY,
        )

        ScatterPointsOverlay(
            entries = entries,
            range = range,
            containerSize = containerSize,
            tokens = tokens,
            onPointClick = { entry ->
                selectedId.value = entry.id
                onPointClick(entry)
            },
            onHoverChange = { id ->
                hoveredId.value = id
                onPointHover(entries.find { it.id == id })
            }
        )

        entries.find { it.id == hoveredId.value }?.let {
            ScatterTooltipOverlay(
                highlighted = it,
                range = range,
                containerSize = containerSize,
                tokens = tokens
            )
        }
    }
}
