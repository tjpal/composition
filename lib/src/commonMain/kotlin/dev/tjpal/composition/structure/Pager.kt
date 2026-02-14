package dev.tjpal.composition.structure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.tjpal.composition.foundation.text.Text
import dev.tjpal.composition.diagrams.themes.tokens.Theme

@Composable
fun SelectionBar(modifier: Modifier, numPages: Int, selectedPageIndex: Int, onPageSelected: (Int) -> Unit = {}) {
    Row(modifier = Modifier.
        height(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.pager.selectionBarHeight)
        then(modifier),
        horizontalArrangement = Arrangement.End) {
        for (i in 0 until numPages) {
            val typography = if (i == selectedPageIndex) {
                _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.pager.selectedPageTypography
            } else {
                _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.pager.unselectedPageTypography
            }

            val interactionSource = remember { MutableInteractionSource() }
            Box(modifier = Modifier
                .clickable(interactionSource = interactionSource, indication = null, onClick =  { onPageSelected(i) })) {
                _root_ide_package_.dev.tjpal.composition.foundation.text.Text("${i + 1}", typography)
            }

            if(i != numPages - 1) {
                Spacer(modifier = Modifier.width(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.pager.selectorSpacing))
            }
        }
    }
}

@Composable
fun Pager(modifier: Modifier, pages: List<@Composable () -> Unit>) {
    val selectedPage = remember { mutableStateOf(0) }

    Column(modifier = Modifier.then(modifier).padding(_root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.pager.contentPadding)) {
        Column(
            modifier = Modifier.
            fillMaxHeight().
            weight(1f)
        ) {
            pages[selectedPage.value]()
        }

        SelectionBar(
            modifier = Modifier.fillMaxWidth(),
            numPages = pages.size,
            selectedPageIndex = selectedPage.value,
            onPageSelected = { selectedPage.value = it }
        )
    }
}
