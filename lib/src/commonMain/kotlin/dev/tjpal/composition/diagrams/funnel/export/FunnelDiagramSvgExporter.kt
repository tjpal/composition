package dev.tjpal.composition.diagrams.funnel.export

import androidx.compose.ui.geometry.Size
import dev.tjpal.composition.diagrams.funnel.internal.computePlacedItems
import dev.tjpal.composition.diagrams.funnel.models.FunnelItem
import dev.tjpal.composition.diagrams.svg.SvgBuilder
import dev.tjpal.composition.diagrams.svg.toSvgColor
import dev.tjpal.composition.diagrams.themes.tokens.FunnelTokens

fun exportFunnelDiagramToSvg(
    items: List<FunnelItem>,
    stages: Int,
    subStagesPerStage: Int,
    categories: Int,
    containerSize: Size,
    tokens: FunnelTokens,
    tooltipProvider: (FunnelItem) -> String? = { it.label },
    linkProvider: (FunnelItem) -> String? = { null }
): String {
    if (stages <= 0 || subStagesPerStage <= 0 || categories <= 0) {
        throw IllegalArgumentException("Stages, sub-stages per stage, and categories must be greater than zero.")
    }

    if (containerSize.width <= 0f || containerSize.height <= 0f) {
        throw IllegalArgumentException("Container size must have positive width and height.")
    }

    val spacingPx = tokens.itemSpacing.value
    val minDiameterPx = tokens.itemMinDiameter.value
    val cellPaddingPx = tokens.itemDefaultCellPadding.value

    val placedItems = computePlacedItems(
        items = items,
        containerSize = containerSize,
        stages = stages,
        subStagesPerStage = subStagesPerStage,
        categories = categories,
        spacingPx = spacingPx,
        minDiameterPx = minDiameterPx,
        cellPaddingPx = cellPaddingPx
    )

    val svgBuilder = SvgBuilder(containerSize.width, containerSize.height)

    svgBuilder.selfClosingTag("rect", mapOf(
        "x" to "0",
        "y" to "0",
        "width" to containerSize.width.toString(),
        "height" to containerSize.height.toString(),
        "fill" to tokens.background.toSvgColor()
    ))

    val totalSubStages = stages * subStagesPerStage

    if (totalSubStages > 0) {
        val innerStroke = tokens.innerSeparatorStrokeWidth.value
        val innerColor = tokens.innerSeparatorColor.toSvgColor()

        for (i in 0..totalSubStages) {
            val x = (i.toFloat() / totalSubStages) * containerSize.width
            svgBuilder.selfClosingTag("line",
                mapOf(
                    "x1" to x.toString(),
                    "y1" to "0",
                    "x2" to x.toString(),
                    "y2" to containerSize.height.toString(),
                    "stroke" to innerColor,
                    "stroke-width" to innerStroke.toString()
                )
            )
        }

        for (i in 0..categories) {
            val y = (i.toFloat() / categories) * containerSize.height
            svgBuilder.selfClosingTag("line",
                mapOf(
                    "x1" to "0",
                    "y1" to y.toString(),
                    "x2" to containerSize.width.toString(),
                    "y2" to y.toString(),
                    "stroke" to innerColor,
                    "stroke-width" to innerStroke.toString()
                )
            )
        }
    }

    val stageStroke = tokens.stageSeparatorStrokeWidth.value
    val stageColor = tokens.stageSeparatorColor.toSvgColor()

    for (i in 0..stages) {
        val x = (i.toFloat() / stages) * containerSize.width
        svgBuilder.selfClosingTag("line",
            mapOf(
                "x1" to x.toString(),
                "y1" to "0",
                "x2" to x.toString(),
                "y2" to containerSize.height.toString(),
                "stroke" to stageColor,
                "stroke-width" to stageStroke.toString()
            )
        )
    }

    placedItems.forEach { placedItem ->
        val link = linkProvider(placedItem.item)
        if (!link.isNullOrBlank()) {
            svgBuilder.openTag("a", mapOf("href" to link))
        }

        svgBuilder.openTag("g")

        val tooltipText = tooltipProvider(placedItem.item)
        if (!tooltipText.isNullOrEmpty()) {
            svgBuilder.openTag("title")
            svgBuilder.addText(tooltipText)
            svgBuilder.closeTag("title")
        }

        svgBuilder.selfClosingTag("circle",
            mapOf(
                "cx" to placedItem.center.x.toString(),
                "cy" to placedItem.center.y.toString(),
                "r" to placedItem.radius.toString(),
                "fill" to placedItem.item.color.toSvgColor()
            )
        )

        svgBuilder.closeTag("g")

        if (!link.isNullOrBlank()) {
            svgBuilder.closeTag("a")
        }
    }

    return svgBuilder.build()
}
