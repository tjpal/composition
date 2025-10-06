package dev.tjpal.gallergy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.basics.functional.Button
import dev.tjpal.foundation.basics.functional.Input
import dev.tjpal.foundation.basics.text.Link
import dev.tjpal.foundation.basics.text.Text
import dev.tjpal.foundation.structure.Pager
import dev.tjpal.foundation.structure.Table
import dev.tjpal.foundation.structure.TableColum
import dev.tjpal.foundation.structure.TableGroup
import dev.tjpal.foundation.structure.cards.CardTwoSlots
import dev.tjpal.foundation.themes.cascade.*
import dev.tjpal.foundation.themes.common.ButtonType
import dev.tjpal.foundation.themes.common.TextType
import dev.tjpal.foundation.themes.common.Theme

@Composable
fun GalleryScreen(content: @Composable () -> Unit) {
    Box(modifier = Modifier.background(Theme.current.background).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun Gallery() {
    Cascade {
        GalleryScreen {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                   modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
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
                Table(
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp).height(150.dp),
                    columnDescriptions = listOf(
                        TableColum("Column 1", 0.3f),
                        TableColum("Column 2", 0.5f),
                        TableColum("Column 3", 0.2f),
                    ),
                    3
                ) { columnIndex, rowIndex ->
                    Text("Cell $columnIndex-$rowIndex", type = TextType.DEFAULT)
                }

                Spacer(modifier = Modifier.height(32.dp))
                Pager(Modifier.width(200.dp).height(100.dp), listOf(
                    { Text("Page 1") },
                    { Text("Page 2") },
                    { Text("Page 3") }
                ))

                Spacer(modifier = Modifier.height(32.dp))
                val headerText = remember { mutableStateOf("Header") }
                CardTwoSlots(
                    headerSlot = {
                        Text(headerText.value, type = TextType.PRIMARY)
                    },
                    contentSlot = {
                        Text("Content", type = TextType.DEFAULT)
                    },
                    onClick = { headerText.value = "Clicked" }
                )

                Spacer(modifier = Modifier.height(32.dp))
                val linkText = remember { mutableStateOf("Link") }
                Link(linkText.value, onClick = { linkText.value = "Clicked" })

                Spacer(modifier = Modifier.height(32.dp))
                Table(
                    modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp).height(250.dp),
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

