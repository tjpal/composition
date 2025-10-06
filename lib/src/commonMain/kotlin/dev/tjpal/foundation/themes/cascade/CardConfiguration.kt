package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.common.CardBasicConfiguration
import dev.tjpal.foundation.themes.common.CardHeaderConfiguration
import dev.tjpal.foundation.themes.common.CardTheme

private val CardCornerRadius = 6f.dp

fun createCardConfiguration(): CardTheme {
    return CardTheme(
        basicConfiguration = CardBasicConfiguration(
            modifier = Modifier.
            shapeShadow(
                highlightShadowColor = HighlightShadowColor,
                castShadowColor = CastShadowColor,
                blurRadius = OutsetShadowBlurRadius,
                offsetY = OutsetShadowOffsetX,
                offsetX = OutsetShadowOffsetY,
                shape = RoundedCornerShape(CardCornerRadius)
            ).
            background(
                BackgroundColor,
                RoundedCornerShape(CardCornerRadius)
            ),
            fundamentalPadding = 8.dp,
            cardTileSize = (64 + 8).dp,
        ),
        headerConfiguration = CardHeaderConfiguration(
            headerHeight = 24.dp
        )
    )
}