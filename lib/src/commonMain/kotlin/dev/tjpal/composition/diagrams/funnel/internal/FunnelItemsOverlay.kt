package dev.tjpal.composition.diagrams.funnel.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.funnel.models.FunnelItem
import dev.tjpal.composition.diagrams.funnel.utilities.mapIndicesToCellRect
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

internal data class PlacedItem(
    val item: FunnelItem,
    val center: Offset,
    val radius: Float
)

@Composable
internal fun FunnelItemsOverlay(
    items: List<FunnelItem>,
    stages: Int,
    subStagesPerStage: Int,
    categories: Int,
    containerSize: Size,
    tokens: dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens,
    onItemClick: (FunnelItem) -> Unit
) {
    val density = LocalDensity.current
    val spacingPx = with(density) { tokens.itemSpacing.toPx() }
    val minDiameterPx = with(density) { tokens.itemMinDiameter.toPx() }
    val cellPaddingPx = with(density) { tokens.itemDefaultCellPadding.toPx() }

    // Place all items based on the number of stages, sub-stages, categories, and container size. Item size also depends on the number
    // of items in each cell.
    val placedItems = remember(items, containerSize.width, containerSize.height, stages, subStagesPerStage, categories) {
        computePlacedItems(
            items = items,
            containerSize = containerSize,
            stages = stages,
            subStagesPerStage = subStagesPerStage,
            categories = categories,
            spacingPx = spacingPx,
            minDiameterPx = minDiameterPx,
            cellPaddingPx = cellPaddingPx
        )
    }

    // State for hovered id to avoid redundant callbacks
    val hoveredIdState = remember { mutableStateOf<String?>(null) }
    val hoveredCenterState = remember { mutableStateOf<Offset?>(null) }
    val tooltipSizeState = remember { mutableStateOf(IntSize(0, 0)) }

    Box(modifier = Modifier) {
        placedItems.forEach { item ->
            val offsetXDp = with(density) { (item.center.x - item.radius).dp }
            val offsetYDp = with(density) { (item.center.y - item.radius).dp }
            val sizeDp = with(density) { -> (item.radius * 2f).dp }

            Box(
                modifier = Modifier
                    .offset(x = offsetXDp, y = offsetYDp)
                    .size(sizeDp)
                    .background(color = item.item.color, shape = CircleShape)
                    .pointerInput(item.item.id) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                val change = event.changes.firstOrNull()

                                when (event.type) {
                                    PointerEventType.Move, PointerEventType.Enter -> {
                                        hoveredIdState.value = item.item.id
                                        hoveredCenterState.value = item.center
                                    }
                                    PointerEventType.Exit -> {
                                        if (hoveredIdState.value == item.item.id) {
                                            hoveredIdState.value = null
                                            hoveredCenterState.value = null
                                        }
                                    }
                                    else -> {
                                        if (change != null && change.changedToUp()) {
                                            onItemClick(item.item)
                                        }
                                    }
                                }
                            }
                        }
                    }
            )
        }

        val hoveredId = hoveredIdState.value

        if (hoveredId != null) {
            val targetItem = placedItems.lastOrNull { it.item.id == hoveredId }
            val label = targetItem?.item?.label

            if (targetItem != null && label != null) {
                val tooltipWidthPx = tooltipSizeState.value.width.toFloat()
                val tooltipHeightPx = tooltipSizeState.value.height.toFloat()

                // position above the circle with a small gap
                val center = hoveredCenterState.value ?: targetItem.center
                val gapPx = with(density) { 4.dp.toPx() }
                val rawX = center.x - tooltipWidthPx / 2f
                val rawY = center.y - targetItem.radius - tooltipHeightPx - gapPx

                val clampedX = rawX.coerceIn(0f, max(0f, containerSize.width - tooltipWidthPx))
                val clampedY = rawY.coerceIn(0f, max(0f, containerSize.height - tooltipHeightPx))

                val xDp = with(density) { clampedX.toDp() }
                val yDp = with(density) { clampedY.toDp() }

                Box(
                    modifier = Modifier
                        .offset(x = xDp, y = yDp)
                        .onGloballyPositioned { coordinates ->
                            // Updates the tooltip size for positioning
                            tooltipSizeState.value = coordinates.size
                        }
                        .then(tokens.tooltipShapeModifier)
                ) {
                    dev.tjpal.composition.foundation.text.Text(label)
                }
            }
        }
    }
}

internal fun computePlacedItems(
    items: List<FunnelItem>,
    containerSize: Size,
    stages: Int,
    subStagesPerStage: Int,
    categories: Int,
    spacingPx: Float,
    minDiameterPx: Float,
    cellPaddingPx: Float
): List<PlacedItem> {
    if (stages <= 0 || subStagesPerStage <= 0 || categories <= 0) {
        return emptyList()
    }

    data class CellKey(val s: Int, val ss: Int, val c: Int)
    val groups: Map<CellKey, List<FunnelItem>> = items.groupBy {
        CellKey(it.stageIndex, it.subStageIndex, it.categoryIndex)
    }

    val result = mutableListOf<PlacedItem>()

    groups.forEach { (cellKey, cellItems) ->
        if (cellItems.isEmpty()) return@forEach

        val cellRect = mapIndicesToCellRect(
            containerSize = containerSize,
            stages = stages,
            subStagesPerStage = subStagesPerStage,
            categories = categories,
            stageIndex = cellKey.s,
            subStageIndex = cellKey.ss,
            categoryIndex = cellKey.c
        )

        val innerLeft = cellRect.left + cellPaddingPx
        val innerTop = cellRect.top + cellPaddingPx
        val innerRight = cellRect.right - cellPaddingPx
        val innerBottom = cellRect.bottom - cellPaddingPx
        val innerWidth = (innerRight - innerLeft).coerceAtLeast(0f)
        val innerHeight = (innerBottom - innerTop).coerceAtLeast(0f)

        if (innerWidth <= 0f || innerHeight <= 0f) {
            return@forEach
        }

        result += computeCellPlacement(
            cellItems = cellItems,
            innerLeft = innerLeft,
            innerTop = innerTop,
            innerRight = innerRight,
            innerBottom = innerBottom,
            spacingPx = spacingPx,
            minDiameterPx = minDiameterPx
        )
    }

    return result
}

internal fun computeCellPlacement(
    cellItems: List<FunnelItem>,
    innerLeft: Float,
    innerTop: Float,
    innerRight: Float,
    innerBottom: Float,
    spacingPx: Float,
    minDiameterPx: Float
): List<PlacedItem> {
    val result = mutableListOf<PlacedItem>()

    val innerWidth = innerRight - innerLeft
    val innerHeight = innerBottom - innerTop

    // Heuristic grid packing: choose columns and rows based on aspect ratio
    val aspect = if (innerHeight > 0f) innerWidth / innerHeight else 1f
    var columns = max(1, ceil(sqrt(cellItems.size * aspect)).toInt())
    var rows = ceil(cellItems.size.toDouble() / columns.toDouble()).toInt()

    val diameterX = (innerWidth - (columns + 1) * spacingPx) / columns
    val diameterY = (innerHeight - (rows + 1) * spacingPx) / rows
    var diameter = min(diameterX, diameterY)

    // Cap diameter so it never exceeds the cell smaller dimension
    val maxAllowedDiameter = min(innerWidth, innerHeight)
    if (diameter > maxAllowedDiameter) {
        diameter = maxAllowedDiameter
    }

    if (diameter < minDiameterPx) {
        diameter = minDiameterPx
    }

    val radius = diameter / 2f
    val slotWidth = diameter + spacingPx
    val slotHeight = diameter + spacingPx

    val sortedItems = cellItems.sortedBy { it.id }
    var itemIndex = 0

    for (row in 0 until rows) {
        for (col in 0 until columns) {
            if (itemIndex >= cellItems.size) {
                break
            }

            val centerX = innerLeft + spacingPx + col * slotWidth + diameter / 2f
            val centerY = innerTop + spacingPx + row * slotHeight + diameter / 2f

            val clampedCx = centerX.coerceIn(innerLeft + radius, innerRight - radius)
            val clampedCy = centerY.coerceIn(innerTop + radius, innerBottom - radius)

            result.add(PlacedItem(item = sortedItems[itemIndex], center = Offset(clampedCx, clampedCy), radius = radius))
            itemIndex++
        }
    }

    return result
}
