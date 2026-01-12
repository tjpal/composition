package dev.tjpal.composition.diagrams.funnel.utilities

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

fun mapIndicesToCellRect(
    containerSize: Size,
    stages: Int,
    subStagesPerStage: Int,
    categories: Int,
    stageIndex: Int,
    subStageIndex: Int,
    categoryIndex: Int
): Rect {
    if (stages <= 0 || subStagesPerStage <= 0 || categories <= 0) {
        return Rect(Offset.Zero, Size.Zero)
    }

    val stageWidth = containerSize.width / stages.toFloat()
    val subStageWidth = stageWidth / subStagesPerStage.toFloat()
    val categoryHeight = containerSize.height / categories.toFloat()

    val s = stageIndex.coerceIn(0, stages - 1)
    val ss = subStageIndex.coerceIn(0, subStagesPerStage - 1)
    val c = categoryIndex.coerceIn(0, categories - 1)

    val left = s * stageWidth + ss * subStageWidth
    val top = c * categoryHeight
    val right = left + subStageWidth
    val bottom = top + categoryHeight

    return Rect(Offset(left, top), Size(right - left, bottom - top))
}
