package previews

import androidx.compose.desktop.ui.tooling.preview.Preview
import dev.tjpal.foundation.themes.cascade.Cascade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.diagrams.DefaultHeatmapHeader
import dev.tjpal.diagrams.DefaultHeatmapRow
import dev.tjpal.diagrams.Heatmap
import dev.tjpal.diagrams.HeatmapDividerRow
import dev.tjpal.foundation.basics.functional.Button
import dev.tjpal.foundation.basics.functional.Input
import dev.tjpal.foundation.basics.spacing.HorizontalDivider
import dev.tjpal.foundation.basics.text.Text
import dev.tjpal.foundation.structure.Pager
import dev.tjpal.foundation.structure.Table
import dev.tjpal.foundation.structure.TableColum
import dev.tjpal.foundation.structure.TableGroup
import dev.tjpal.foundation.structure.cards.CardTwoSlots
import dev.tjpal.foundation.themes.cascade.BackgroundColor
import dev.tjpal.foundation.themes.tokens.ButtonType
import dev.tjpal.foundation.themes.tokens.DividerType
import dev.tjpal.foundation.themes.tokens.TextType

@Preview
@Composable
fun PagerPreview() {
    PreviewScreen {
        Cascade {
            Pager(Modifier.width(200.dp).height(100.dp), listOf(
                { Text("Page 1") },
                { Text("Page 2") },
                { Text("Page 3") }
            ))
        }
    }
}

@Composable
@Preview
fun ButtonPreview() {
    PreviewScreen {
        Cascade {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(type = ButtonType.PRIMARY) {
                    Text("Primary Button", type = TextType.PRIMARY)
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(type = ButtonType.DEFAULT) {
                    Text("Default Button", type = TextType.DEFAULT)
                }
                Spacer(modifier = Modifier.height(32.dp))
                Input(modifier = Modifier.height(32.dp).width(200.dp))
                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(type = DividerType.PRIMARY)
                Spacer(modifier = Modifier.height(32.dp))
                HorizontalDivider(type = DividerType.SECONDARY)
                Spacer(modifier = Modifier.height(32.dp))
                Table(
                    modifier = Modifier.fillMaxWidth(),
                    columnDescriptions = listOf(
                        TableColum("Column 1", 0.3f),
                        TableColum("Column 2", 0.5f),
                        TableColum("Column 3", 0.2f),
                    ),
                    3
                ) {
                        columnIndex, rowIndex ->
                    Text("Cell $columnIndex-$rowIndex", type = TextType.DEFAULT)
                }
                Spacer(modifier = Modifier.height(32.dp))
                CardTwoSlots(
                    headerSlot = {
                        Text("Header")
                    },
                    contentSlot = {
                        Text("Content")
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
                Table(
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
                    columnDescriptions = listOf(
                        TableColum("Column 1", 0.3f),
                        TableColum("Column 2", 0.5f),
                        TableColum("Column 3", 0.2f),
                    ),
                    groupDescriptions = listOf(
                        TableGroup("Group 1", 3),
                        TableGroup("Group 2", 2),
                    )
                ) { groupIndex, columnIndex, rowIndex ->
                    Text("Cell $groupIndex-$columnIndex-$rowIndex", type = TextType.DEFAULT)
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewHeatmap() {
    PreviewScreen {
        Cascade {
            Heatmap(
                headerTexts = listOf(
                    DefaultHeatmapHeader("-2"),
                    DefaultHeatmapHeader(""),
                    DefaultHeatmapHeader("T"),
                    DefaultHeatmapHeader(""),
                    DefaultHeatmapHeader("+2")
                ),
                listOf(
                    DefaultHeatmapRow("Test 1", listOf(0, 1, 2, 3, 4)),
                    DefaultHeatmapRow("Test 2", listOf(3, 2, 0, 4, 1)),
                    HeatmapDividerRow(),
                    DefaultHeatmapRow("Test 3", listOf(1, 0, 2, 3, 4)),
                ),
            )
        }
    }
}

@Composable
fun PreviewScreen(content: @Composable () -> Unit) {
    Box(modifier = Modifier.
    background(BackgroundColor).
    width(800.dp).
    height(800.dp).
    padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}