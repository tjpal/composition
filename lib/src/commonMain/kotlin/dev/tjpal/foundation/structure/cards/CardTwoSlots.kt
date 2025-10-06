package dev.tjpal.foundation.structure.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tjpal.foundation.themes.common.Theme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardTwoSlots(
    modifier: Modifier = Modifier,
    headerSlot: @Composable () -> Unit,
    contentSlot: @Composable () -> Unit,
    onClick: () -> Unit = {}
) {
    val theme = Theme.current.cardTheme
    val padding = theme.basicConfiguration.fundamentalPadding

    FullCardFrame(modifier = modifier.
        padding(PaddingValues(padding * 2, padding, padding * 2, padding)).onClick { onClick() }
    ) {
        Column() {
            Box(modifier = Modifier.height(theme.headerConfiguration.headerHeight)) {
                headerSlot()
            }
            contentSlot()
        }
    }
}