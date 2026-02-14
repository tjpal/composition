package dev.tjpal.composition.structure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.foundation.spacing.HorizontalDivider
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.DividerType
import dev.tjpal.composition.diagrams.themes.tokens.TextType
import dev.tjpal.composition.diagrams.themes.tokens.Theme

data class TableColum(
    val name: String,
    val weight: Float
)

data class TableGroup(
    val name: String,
    val rowCount: Int
)

@Composable
fun Table(
    modifier: Modifier = Modifier,
    columnDescriptions: List<TableColum>,
    rowCount: Int,
    cellContent: @Composable (rowIndex: Int, columnIndex: Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth().then(modifier)) {
        item {
            TableHeader(columnDescriptions)
        }
        item {
            _root_ide_package_.dev.tjpal.composition.foundation.spacing.HorizontalDivider(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerType.SECONDARY)
            Spacer(modifier = Modifier.height(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.table.contentVerticalPadding))
        }
        items(rowCount) { rowIndex ->
            TableRow(columnDescriptions, rowIndex, cellContent)
        }
    }
}

@Composable
fun Table(
    modifier: Modifier = Modifier,
    columnDescriptions: List<TableColum>,
    groupDescriptions: List<TableGroup>,
    cellContent: @Composable (groupIndex: Int, rowIndex: Int, columnIndex: Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth().then(modifier)) {
        item {
            TableHeader(columnDescriptions)
        }
        item {
            _root_ide_package_.dev.tjpal.composition.foundation.spacing.HorizontalDivider(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerType.SECONDARY)
            Spacer(modifier = Modifier.height(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.table.contentVerticalPadding))
        }
        groupDescriptions.forEachIndexed { groupIndex, group ->
            item {
                Spacer(modifier = Modifier.height(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.table.groupVerticalPadding))
                _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                    group.name,
                    type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PRIMARY
                )
                Spacer(modifier = Modifier.height(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.table.groupVerticalPadding))
            }
            items(group.rowCount) { rowIndex ->
                TableRow(columnDescriptions, rowIndex) @Composable { row, column ->
                    cellContent(groupIndex, row, column)
                }
            }
        }
    }
}

@Composable
fun TableHeader(columnDescriptions: List<TableColum>) {
    Row() {
        columnDescriptions.forEach { description ->
            _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                description.name,
                type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PRIMARY,
                modifier = Modifier.fillMaxWidth().weight(description.weight).padding(
                    0.dp,
                    _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.table.headerVerticalPadding
                )
            )
        }
    }
}

@Composable
fun TableRow(
    columnDescriptions: List<TableColum>,
    rowIndex: Int,
    cellContent: @Composable (rowIndex: Int, columnIndex: Int) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        columnDescriptions.forEachIndexed { columnIndex, data ->
            Box(modifier = Modifier.fillMaxWidth().weight(data.weight).padding(0.dp, _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.table.contentVerticalPadding)) {
                cellContent(rowIndex, columnIndex)
            }
        }
    }
}