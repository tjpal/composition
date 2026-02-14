package dev.tjpal.composition.diagrams.funnel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import dev.tjpal.composition.diagrams.funnel.internal.FunnelGridCanvas
import dev.tjpal.composition.diagrams.funnel.internal.FunnelItemsOverlay
import dev.tjpal.composition.diagrams.funnel.models.FunnelItem
import dev.tjpal.composition.diagrams.themes.tokens.Theme

@Composable
fun FunnelDiagram(
    modifier: Modifier = Modifier,
    stages: Int,
    subStagesPerStage: Int,
    categories: Int,
    items: List<FunnelItem>,
    onItemClick: (FunnelItem) -> Unit = {}
) {
    val tokens = dev.tjpal.composition.diagrams.themes.tokens.Theme.current.funnel

    BoxWithConstraints(modifier = modifier.background(tokens.background)) {
        var containerSize = Size(constraints.maxWidth.toFloat(), constraints.maxHeight.toFloat())

        Box(modifier = Modifier.fillMaxSize()) {
            FunnelGridCanvas(
                modifier = Modifier.fillMaxSize(),
                stages = stages,
                subStagesPerStage = subStagesPerStage,
                categories = categories,
                tokens = tokens
            )

            FunnelItemsOverlay(
                items = items,
                stages = stages,
                subStagesPerStage = subStagesPerStage,
                categories = categories,
                containerSize = containerSize,
                tokens = tokens,
                onItemClick = onItemClick
            )
        }
    }
}
