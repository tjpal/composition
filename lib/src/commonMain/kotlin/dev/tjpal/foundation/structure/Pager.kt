package dev.tjpal.foundation.structure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.tjpal.foundation.basics.text.Text
import dev.tjpal.foundation.themes.common.Theme
import dev.tjpal.foundation.utilities.NoIndication

@Composable
fun SelectionBar(modifier: Modifier, numPages: Int, selectedPageIndex: Int, onPageSelected: (Int) -> Unit = {}) {
    Row(modifier = Modifier.
        height(Theme.current.pagerTheme.selectionBarHeight)
        then(modifier),
        horizontalArrangement = Arrangement.End) {
        for (i in 0 until numPages) {
            val typography = if (i == selectedPageIndex) {
                Theme.current.pagerTheme.selectedPageTypography
            } else {
                Theme.current.pagerTheme.unselectedPageTypography
            }

            val interactionSource = remember { MutableInteractionSource() }
            Box(modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = NoIndication,
                onClick =  { onPageSelected(i) }
            )
            ) {
                Text("${i + 1}", typography)
            }

            if(i != numPages - 1) {
                Spacer(modifier = Modifier.width(Theme.current.pagerTheme.selectorSpacing))
            }
        }
    }
}

@Composable
fun Pager(modifier: Modifier, pages: List<@Composable () -> Unit>) {
    val selectedPage = remember { mutableStateOf(0) }

    Column(modifier = Modifier.then(modifier).padding(Theme.current.pagerTheme.contentPadding)) {
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
