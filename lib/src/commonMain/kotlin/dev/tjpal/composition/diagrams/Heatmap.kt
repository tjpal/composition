package dev.tjpal.composition.diagrams

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.spacing.HorizontalDivider
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.TextType
import dev.tjpal.composition.diagrams.themes.tokens.Theme

interface HeatmapRow {
    val name: String
    val numValues: Int
}

interface HeatmapHeader {
    val title: String
}
data class DefaultHeatmapRow(
    override val name: String,
    val values: List<Int>
): HeatmapRow {
    override val numValues: Int = values.size
}

class HeatmapDividerRow: HeatmapRow {
    override val name: String = ""
    override val numValues: Int = 0
}

data class DefaultHeatmapHeader(
    override val title: String
): HeatmapHeader

@Composable
fun DefaultRowTitleRenderer(row: HeatmapRow) {
    Text(row.name, type = TextType.DEFAULT)
}

@Composable
fun DefaultRowCellRenderer(row: HeatmapRow, index: Int) {
    val defaultRow = row as DefaultHeatmapRow

    Box(
        modifier = Modifier.
        fillMaxSize().
        background(
            Color(0, 40 * defaultRow.values[index] + 40, 0)
        )
    )
}

data class HeatmapConfig(
    val rowTextWidth: Dp = 48.dp,
    val defaultRowTitleRenderer: @Composable (HeatmapRow) -> Unit = {
        DefaultRowTitleRenderer(it)
    },
    val defaultRowCellRenderer: @Composable (HeatmapRow, Int) -> Unit = { row, index ->
        DefaultRowCellRenderer(row, index)
    },
)

@Composable
fun HeatmapHeader(headers: List<HeatmapHeader>, config: HeatmapConfig) {
    val theme = Theme.current.heatmap

    Row(modifier = Modifier.fillMaxWidth().padding(theme.cellPadding)) {
        Spacer(modifier = Modifier.width(config.rowTextWidth))

        headers.forEach { header ->
            Text(
                text = header.title,
                type = TextType.DEFAULT,
                modifier = Modifier.size(theme.cellSize)
            )
        }
    }
}

@Composable
fun HeatmapBody(rows: List<HeatmapRow>, config: HeatmapConfig) {
    val theme = Theme.current.heatmap

    rows.forEach { row ->
        Row(modifier = Modifier.fillMaxWidth().padding(theme.cellPadding)) {

            when(row) {
                is HeatmapDividerRow -> {
                    HorizontalDivider()
                }
                else -> {
                    Box(modifier = Modifier.width(config.rowTextWidth)) {
                        config.defaultRowTitleRenderer(row)
                    }

                    for(columnIndex in 0 until row.numValues) {
                        Box(modifier = Modifier.size(theme.cellSize).padding(theme.cellPadding)) {
                            config.defaultRowCellRenderer(row, columnIndex)
                        }
                    }
                }
            }
        }
    }}

@Composable
fun Heatmap(
    headerTexts: List<HeatmapHeader>,
    rows: List<HeatmapRow>,
    config: HeatmapConfig = HeatmapConfig(),
    modifier: Modifier = Modifier,
) {
    val theme = Theme.current.heatmap

    Box(modifier = modifier) {
        Column(modifier = Modifier.width(config.rowTextWidth + (theme.cellSize + theme.cellPadding) * headerTexts.size  - theme.cellPadding)) {
            HeatmapHeader(headerTexts, config)
            HorizontalDivider()
            Spacer(modifier = Modifier.height(theme.cellPadding))
            HeatmapBody(rows, config)
        }
    }
}