package dev.tjpal.composition.diagrams.scatter.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import dev.tjpal.composition.diagrams.scatter.ScatterPoint
import dev.tjpal.composition.diagrams.scatter.models.DiagramRange
import dev.tjpal.composition.diagrams.scatter.models.ScatterEntry
import dev.tjpal.composition.diagrams.scatter.utilities.mapToPixel
import dev.tjpal.composition.foundation.themes.tokens.ScatterTokens

@Composable
internal fun <T> ScatterPointsOverlay(
    entries: List<ScatterEntry<T>>,
    range: DiagramRange,
    containerSize: Size,
    tokens: ScatterTokens,
    onPointClick: (ScatterEntry<T>) -> Unit,
    onHoverChange: (String?) -> Unit
) {
    if (containerSize.width <= 0f || containerSize.height <= 0f) {
        return
    }

    val density = LocalDensity.current

    entries.forEach { entry ->
        key(entry.id) {
            val position = mapToPixel(range, containerSize, 0f, 0f, entry.x, entry.y)

            val offsetXDp = with(density) { position.x.toDp() } - tokens.pointRadius
            val offsetYDp = with(density) { position.y.toDp() } - tokens.pointRadius

            Box(modifier = Modifier.offset(offsetXDp, offsetYDp)) {
                ScatterPoint(
                    entry = entry,
                    tokens = tokens,
                    onClick = { onPointClick(it) },
                    onHoverChanged = { hovered -> if (hovered) onHoverChange(entry.id) else onHoverChange(null) }
                )
            }
        }
    }
}
