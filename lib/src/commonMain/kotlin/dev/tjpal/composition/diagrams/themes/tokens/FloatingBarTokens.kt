package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class FloatingBarOrientation {
    HORIZONTAL,
    VERTICAL
}

enum class FloatingBarLocation {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT
}

data class FloatingBarTokens(
    val modifier: Modifier = Modifier,
    /** Padding applied to each button inside the floating bar */
    val buttonPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
    /** Horizontal padding applied to the divider on each side. Total space consumed by divider per-separator is dividerHorizontalPadding*2 + dividerThickness */
    val dividerHorizontalPadding: Dp = 8.dp,
    /** Vertical padding applied to the divider top/bottom */
    val dividerVerticalPadding: Dp = 4.dp,
    /** Visual thickness of the divider line */
    val dividerThickness: Dp = 1.dp,
    /** Corner radius for the bar itself */
    val cornerRadius: Dp = 8.dp
)
