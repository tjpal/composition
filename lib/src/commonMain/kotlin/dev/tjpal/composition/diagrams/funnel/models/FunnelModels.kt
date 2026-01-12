package dev.tjpal.composition.diagrams.funnel.models

import androidx.compose.ui.graphics.Color

data class FunnelItem(
    val id: String,
    val stageIndex: Int,
    val subStageIndex: Int,
    val categoryIndex: Int,
    val color: Color,
    val label: String? = null
)
