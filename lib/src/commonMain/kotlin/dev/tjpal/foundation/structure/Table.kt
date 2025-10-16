package dev.tjpal.foundation.structure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.basics.spacing.HorizontalDivider
import dev.tjpal.foundation.basics.text.Text
import dev.tjpal.foundation.themes.tokens.DividerType
import dev.tjpal.foundation.themes.tokens.TextType
import dev.tjpal.foundation.themes.tokens.Theme

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
            HorizontalDivider(type = DividerType.SECONDARY)
            Spacer(modifier = Modifier.height(Theme.current.table.contentVerticalPadding))
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
            HorizontalDivider(type = DividerType.SECONDARY)
            Spacer(modifier = Modifier.height(Theme.current.table.contentVerticalPadding))
        }
        groupDescriptions.forEachIndexed { groupIndex, group ->
            item {
                Spacer(modifier = Modifier.height(Theme.current.table.groupVerticalPadding))
                Text(
                    group.name,
                    type = TextType.PRIMARY)
                Spacer(modifier = Modifier.height(Theme.current.table.groupVerticalPadding))
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
            Text(
                description.name,
                type = TextType.PRIMARY,
                modifier = Modifier.
                fillMaxWidth().
                weight(description.weight).
                padding(0.dp, Theme.current.table.headerVerticalPadding)
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
            Box(modifier = Modifier.fillMaxWidth().weight(data.weight).padding(0.dp, Theme.current.table.contentVerticalPadding)) {
                cellContent(rowIndex, columnIndex)
            }
        }
    }
}