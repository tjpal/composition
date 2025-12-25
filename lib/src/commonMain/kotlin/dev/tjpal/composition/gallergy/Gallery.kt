package dev.tjpal.composition.gallergy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.scatter.ScatterDiagramPreview
import dev.tjpal.composition.foundation.basics.functional.Button
import dev.tjpal.composition.foundation.basics.functional.FloatingBar
import dev.tjpal.composition.foundation.basics.functional.Input
import dev.tjpal.composition.foundation.basics.functional.MultiLineInput
import dev.tjpal.composition.foundation.basics.functional.WaitingCircle
import dev.tjpal.composition.foundation.basics.text.Link
import dev.tjpal.composition.foundation.basics.text.Text
import dev.tjpal.composition.foundation.structure.Pager
import dev.tjpal.composition.foundation.structure.Table
import dev.tjpal.composition.foundation.structure.TableColum
import dev.tjpal.composition.foundation.structure.TableGroup
import dev.tjpal.composition.foundation.structure.cards.CardTwoSlots
import dev.tjpal.composition.foundation.structure.graphs.Connector
import dev.tjpal.composition.foundation.structure.graphs.EdgeSide
import dev.tjpal.composition.foundation.structure.graphs.EdgeSpec
import dev.tjpal.composition.foundation.structure.graphs.GraphEditor
import dev.tjpal.composition.foundation.structure.graphs.GraphState
import dev.tjpal.composition.foundation.structure.graphs.NodeSpec
import dev.tjpal.composition.foundation.templates.FloatingBarTemplate
import dev.tjpal.composition.foundation.themes.cascade.Cascade
import dev.tjpal.composition.foundation.themes.tokens.ButtonType
import dev.tjpal.composition.foundation.themes.tokens.FloatingBarLocation
import dev.tjpal.composition.foundation.themes.tokens.FloatingBarOrientation
import dev.tjpal.composition.foundation.themes.tokens.NodeShape
import dev.tjpal.composition.foundation.themes.tokens.TextType
import dev.tjpal.composition.foundation.themes.tokens.Theme
import dev.tjpal.composition.foundation.utilities.zoom.InitialScaleMode
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun GalleryScreen(content: @Composable () -> Unit) {
    Box(modifier = Modifier.background(Theme.current.background).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GraphEditorDemoScreen() {
    val gridSpacing = 24.dp

    val nodes = remember {
        listOf(
            NodeSpec(
                id = "A",
                shape = NodeShape.CIRCLE,
                initialPosition = Offset(80f, 160f),
                widthMultiplier = 3,
                heightMultiplier = 3,
                connectors = listOf(Connector("A1", EdgeSide.RIGHT, 1), Connector("A2", EdgeSide.RIGHT, 0)),
                onTap = { println("Tapped node A") }
            ) { id -> Text(id) },
            NodeSpec(
                id = "B",
                shape = NodeShape.LEFT_ROUNDED,
                widthMultiplier = 2,
                heightMultiplier = 2,
                initialPosition = Offset(160f, 160f),
                connectors = listOf(Connector("B1", EdgeSide.TOP, 1), Connector("B2", EdgeSide.TOP, 2)),
                onTap = { println("Tapped node B") }
            ) { id -> Text(id) },
            NodeSpec(
                id = "C",
                shape = NodeShape.RIGHT_ROUNDED,
                initialPosition = Offset(280f, 120f),
                connectors = listOf(Connector("C1", EdgeSide.LEFT, 0), Connector("C2", EdgeSide.TOP, 1)),
                onTap = { println("Tapped node C") }
            ) { id -> Text(id) },
        )
    }

    val edges = remember {
        MutableStateFlow(
            mutableListOf(
                EdgeSpec(fromNodeId = "A", toNodeId = "B", fromConnectorId = "A1", toConnectorId = "B1"),
                EdgeSpec(fromNodeId = "B", toNodeId = "C", fromConnectorId = "B1", toConnectorId = "C1"),
                EdgeSpec(fromNodeId = "A", toNodeId = "C", fromConnectorId = "A1", toConnectorId = "C2"),
            )
        )
    }

    val graphState = remember { GraphState(nodes) }

    Box(
        modifier = Modifier.width(1024.dp).height(768.dp)
    ) {
        val onConnected = { fromNodeId: String, fromConnectorId: String, toNodeId: String, toConnectorId: String ->
            val newEdgeList = edges.value + mutableListOf(EdgeSpec(
                fromNodeId = fromNodeId,
                toNodeId = toNodeId,
                fromConnectorId = fromConnectorId,
                toConnectorId = toConnectorId)
            )
            edges.value = newEdgeList.toMutableList()

            println("Connected $fromNodeId:$fromConnectorId to $toNodeId:$toConnectorId")
        }

        val onDisconnected = { nodeId: String, connectorId: String ->
            val newEdgeList = edges.value.filter {
                !((it.fromNodeId == nodeId && it.fromConnectorId == connectorId) ||
                  (it.toNodeId == nodeId && it.toConnectorId == connectorId))
            }
            edges.value = newEdgeList.toMutableList()

            println("Disconnected $nodeId:$connectorId")
        }

        val dynEdgeList = edges.collectAsState()

        GraphEditor(
            state = graphState,
            nodes = nodes,
            edges = dynEdgeList.value,
            gridSpacing = gridSpacing,
            gridExtension = 2000f,
            initialScaleMode = InitialScaleMode.DEFAULT,
            onConnect = onConnected,
            onDisconnect = onDisconnected
        )
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

                ScatterDiagramPreview()

                WaitingCircle()

                FloatingBarTemplate(
                    modifier = Modifier.width(1024.dp).height(768.dp),
                    location = FloatingBarLocation.BOTTOM,
                    barInset = 8.dp,
                    bandThickness = 64.dp,
                    floatingBar = {
                        FloatingBar(
                            buttonExtent = 64.dp,
                            orientation = FloatingBarOrientation.HORIZONTAL
                        ) {
                            group {
                                item {
                                    Button(type = ButtonType.SHY) {
                                        Text("A")
                                    }
                                }
                                item {
                                    Button(type = ButtonType.SHY) {
                                        Text("B")
                                    }
                                }
                            }
                            group {
                                item {
                                    Button(type = ButtonType.SHY) {
                                        Text("C")
                                    }
                                }
                            }
                        }
                    },
                    content = {
                        GraphEditorDemoScreen()
                    }
                )


                Spacer(modifier = Modifier.height(32.dp))
                Button(type = ButtonType.DEFAULT) {
                    Text("Default Button", type = TextType.DEFAULT)
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(type = ButtonType.SHY) {
                    Text("Shy Button", type = TextType.DEFAULT)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Spacer(modifier = Modifier.height(32.dp))
                Input(modifier = Modifier.height(32.dp).width(200.dp), placeholder = "Enter text")
                Spacer(modifier = Modifier.height(32.dp))

                var text by remember { mutableStateOf("") }
                MultiLineInput(
                    modifier = Modifier.width(400.dp),
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    }
                )

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
