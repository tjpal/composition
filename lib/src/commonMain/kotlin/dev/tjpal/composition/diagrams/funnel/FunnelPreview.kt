package dev.tjpal.composition.diagrams.funnel

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.funnel.models.FunnelItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, widthDp = 820, heightDp = 300)
@Composable
fun FunnelDiagramPreview() {
    val stages = 4
    val subStagesPerStage = 3
    val categories = 3

    val colors = listOf(
        Color(0xFF1E88E5), Color(0xFFD81B60), Color(0xFF43A047), Color(0xFFF4511E),
        Color(0xFF8E24AA), Color(0xFF00ACC1), Color(0xFFFFC107), Color(0xFF6D4C41)
    )

    val sampleItems = mutableListOf<FunnelItem>()

    var idCounter = 0
    for (s in 0 until stages) {
        for (ss in 0 until subStagesPerStage) {
            for (c in 0 until categories) {
                val count = (s + 1) + (ss % 3)
                repeat(count) {
                    sampleItems.add(
                        FunnelItem(
                            id = "i${idCounter}",
                            stageIndex = s,
                            subStageIndex = ss,
                            categoryIndex = c,
                            color = colors[idCounter % colors.size],
                            label = "Stage $s / Sub $ss / Cat $c",
                        )
                    )
                    idCounter++
                }
            }
        }
    }

    FunnelDiagram(
        modifier = Modifier.width(800.dp).height(800.dp),
        stages = stages,
        subStagesPerStage = subStagesPerStage,
        categories = categories,
        items = sampleItems,
        onItemClick = { /* noop for preview */ }
    )
}
