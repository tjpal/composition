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
import dev.tjpal.composition.diagrams.funnel.FunnelDiagramPreview
import dev.tjpal.composition.diagrams.scatter.ScatterDiagramPreview
import dev.tjpal.composition.foundation.functional.Button
import dev.tjpal.composition.foundation.functional.FloatingBar
import dev.tjpal.composition.foundation.functional.Input
import dev.tjpal.composition.foundation.functional.MultiLineInput
import dev.tjpal.composition.foundation.functional.WaitingCircle
import dev.tjpal.composition.foundation.text.Link
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.structure.Pager
import dev.tjpal.composition.structure.Table
import dev.tjpal.composition.structure.TableColum
import dev.tjpal.composition.structure.TableGroup
import dev.tjpal.composition.structure.cards.CardTwoSlots
import dev.tjpal.composition.structure.graphs.Connector
import dev.tjpal.composition.structure.graphs.EdgeSide
import dev.tjpal.composition.structure.graphs.EdgeSpec
import dev.tjpal.composition.structure.graphs.GraphEditor
import dev.tjpal.composition.structure.graphs.GraphState
import dev.tjpal.composition.structure.graphs.NodeSpec
import dev.tjpal.composition.templates.FloatingBarTemplate
import dev.tjpal.composition.foundation.functional.zoom.InitialScaleMode
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun GalleryScreen(content: @Composable () -> Unit) {
    Box(modifier = Modifier.background(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.background).fillMaxSize(),
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
                shape = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.NodeShape.CIRCLE,
                initialPosition = Offset(80f, 160f),
                widthMultiplier = 3,
                heightMultiplier = 3,
                connectors = listOf(Connector("A1", EdgeSide.RIGHT, 1, allowsMultipleConnections = true), Connector("A2", EdgeSide.RIGHT, 0)),
                onTap = { println("Tapped node A") }
            ) { id -> _root_ide_package_.dev.tjpal.composition.foundation.text.Text(id) },
            NodeSpec(
                id = "B",
                shape = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.NodeShape.LEFT_ROUNDED,
                widthMultiplier = 2,
                heightMultiplier = 2,
                initialPosition = Offset(160f, 160f),
                connectors = listOf(Connector("B1", EdgeSide.TOP, 1), Connector("B2", EdgeSide.TOP, 2)),
                onTap = { println("Tapped node B") }
            ) { id -> _root_ide_package_.dev.tjpal.composition.foundation.text.Text(id) },
            NodeSpec(
                id = "C",
                shape = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.NodeShape.RIGHT_ROUNDED,
                initialPosition = Offset(280f, 120f),
                connectors = listOf(Connector("C1", EdgeSide.LEFT, 0), Connector("C2", EdgeSide.TOP, 1)),
                onTap = { println("Tapped node C") }
            ) { id -> _root_ide_package_.dev.tjpal.composition.foundation.text.Text(id) },
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
            initialScaleMode = _root_ide_package_.dev.tjpal.composition.foundation.functional.zoom.InitialScaleMode.DEFAULT,
            onConnect = onConnected,
            onDisconnect = onDisconnected
        )
    }
}

@Composable
fun Gallery() {
    _root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.Cascade {
        GalleryScreen {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                _root_ide_package_.dev.tjpal.composition.foundation.functional.Button(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.PRIMARY) {
                    _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                        "Primary Button",
                        type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PRIMARY
                    )
                }

                FunnelDiagramPreview()
                ScatterDiagramPreview()

                _root_ide_package_.dev.tjpal.composition.foundation.functional.WaitingCircle()

                FloatingBarTemplate(
                    modifier = Modifier.width(1024.dp).height(768.dp),
                    location = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FloatingBarLocation.BOTTOM,
                    barInset = 8.dp,
                    bandThickness = 64.dp,
                    floatingBar = {
                        _root_ide_package_.dev.tjpal.composition.foundation.functional.FloatingBar(
                            buttonExtent = 64.dp,
                            orientation = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FloatingBarOrientation.HORIZONTAL
                        ) {
                            group {
                                item {
                                    _root_ide_package_.dev.tjpal.composition.foundation.functional.Button(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.SHY) {
                                        _root_ide_package_.dev.tjpal.composition.foundation.text.Text("A")
                                    }
                                }
                                item {
                                    _root_ide_package_.dev.tjpal.composition.foundation.functional.Button(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.SHY) {
                                        _root_ide_package_.dev.tjpal.composition.foundation.text.Text("B")
                                    }
                                }
                            }
                            group {
                                item {
                                    _root_ide_package_.dev.tjpal.composition.foundation.functional.Button(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.SHY) {
                                        _root_ide_package_.dev.tjpal.composition.foundation.text.Text("C")
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
                _root_ide_package_.dev.tjpal.composition.foundation.functional.Button(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.DEFAULT) {
                    _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                        "Default Button",
                        type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                _root_ide_package_.dev.tjpal.composition.foundation.functional.Button(type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.SHY) {
                    _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                        "Shy Button",
                        type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Spacer(modifier = Modifier.height(32.dp))
                _root_ide_package_.dev.tjpal.composition.foundation.functional.Input(
                    modifier = Modifier.height(32.dp).width(200.dp), placeholder = "Enter text"
                )
                Spacer(modifier = Modifier.height(32.dp))

                var text by remember { mutableStateOf("") }
                _root_ide_package_.dev.tjpal.composition.foundation.functional.MultiLineInput(
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
                    _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                        "Cell $columnIndex-$rowIndex",
                        type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Pager(
                    Modifier.width(200.dp).height(100.dp), listOf(
                    { _root_ide_package_.dev.tjpal.composition.foundation.text.Text("Page 1") },
                    { _root_ide_package_.dev.tjpal.composition.foundation.text.Text("Page 2") },
                    { _root_ide_package_.dev.tjpal.composition.foundation.text.Text("Page 3") }
                ))

                Spacer(modifier = Modifier.height(32.dp))
                val headerText = remember { mutableStateOf("Header") }
                CardTwoSlots(
                    headerSlot = {
                        _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                            headerText.value,
                            type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PRIMARY
                        )
                    },
                    contentSlot = {
                        _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                            "Content",
                            type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT
                        )
                    },
                    onClick = { headerText.value = "Clicked" }
                )

                Spacer(modifier = Modifier.height(32.dp))
                val linkText = remember { mutableStateOf("Link") }
                _root_ide_package_.dev.tjpal.composition.foundation.text.Link(
                    linkText.value,
                    onClick = { linkText.value = "Clicked" })

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
                    _root_ide_package_.dev.tjpal.composition.foundation.text.Text(
                        "Cell $groupIndex-$columnIndex-$rowIndex",
                        type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT
                    )
                }
            }
        }
    }
}
