package dev.tjpal.foundation.themes.cascade

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.foundation.themes.tokens.CardBasicConfiguration
import dev.tjpal.foundation.themes.tokens.CardHeaderConfiguration
import dev.tjpal.foundation.themes.tokens.CardTokens

private val CardCornerRadius = 6f.dp

fun createCardConfiguration(): CardTokens {
    return CardTokens(
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