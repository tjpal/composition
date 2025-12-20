package dev.tjpal.composition.diagrams.scatter

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.scatter.models.DiagramRange
import dev.tjpal.composition.diagrams.scatter.models.ScatterEntry
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, widthDp = 820, heightDp = 820)
@Composable
fun ScatterDiagramPreview() {
    val range = DiagramRange(minX = -1f, maxX = 1f, minY = -1f, maxY = 1f)
    val colors = listOf(
        Color(0xFF1E88E5), Color(0xFFD81B60), Color(0xFF43A047), Color(0xFFF4511E),
        Color(0xFF8E24AA), Color(0xFF00ACC1), Color(0xFFFFC107), Color(0xFF6D4C41)
    )

    val entries = List(10) { i ->
        ScatterEntry(
            id = "p$i",
            x = ((i % 5) - 2) * 0.4f + (i * 0.03f),
            y = ((i / 5) - 1) * 0.6f + (i * 0.02f),
            label = "Item $i",
            color = colors[i % colors.size],
            data = null
        )
    }

    ScatterDiagram(
        modifier = Modifier.width(800.dp).height(800.dp),
        range = range,
        entries = entries,
        onPointClick = { /* noop for preview */ },
        onPointHover = { /* noop for preview */ }
    )
}
