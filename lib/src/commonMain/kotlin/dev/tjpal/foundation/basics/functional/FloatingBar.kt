package dev.tjpal.foundation.basics.functional

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.tjpal.foundation.basics.spacing.VerticalDivider
import dev.tjpal.foundation.basics.spacing.HorizontalDivider
import dev.tjpal.foundation.themes.tokens.DividerType
import dev.tjpal.foundation.themes.tokens.FloatingBarOrientation
import dev.tjpal.foundation.themes.tokens.Theme


@Composable
fun FloatingBar(
    modifier: Modifier = Modifier,
    buttonExtent: Dp,
    groups: List<List<@Composable () -> Unit>>,
    orientation: FloatingBarOrientation = FloatingBarOrientation.HORIZONTAL
) {
    if (groups.isEmpty()) return

    val tokens = Theme.current.floatingBar
    val totalButtons = groups.sumOf { it.size }
    val separators = (groups.size - 1).coerceAtLeast(0)

    if (orientation == FloatingBarOrientation.HORIZONTAL) {
        // total width = buttons + separator spacing + separator thickness
        val totalWidth: Dp = buttonExtent * totalButtons + (tokens.dividerHorizontalPadding * 2 * separators) + (tokens.dividerThickness * separators)

        Row(
            modifier = modifier
                .width(totalWidth)
                .then(tokens.modifier)
        ) {
            groups.forEachIndexed { groupIndex, group ->
                group.forEach { buttonContent ->
                    Box(
                        modifier = Modifier
                            .width(buttonExtent)
                            .padding(tokens.buttonPadding)
                    ) {
                        buttonContent()
                    }
                }

                // insert vertical divider between groups
                if (groupIndex != groups.lastIndex) {
                    VerticalDivider(
                        type = DividerType.PRIMARY,
                        horizontalPadding = tokens.dividerHorizontalPadding,
                        verticalPadding = tokens.dividerVerticalPadding,
                        thickness = tokens.dividerThickness
                    )
                }
            }
        }
    } else if(orientation == FloatingBarOrientation.VERTICAL) {
        // total height = buttons + separator spacing + separator thickness
        val totalHeight: Dp = buttonExtent * totalButtons + (tokens.dividerVerticalPadding * 2 * separators) + (tokens.dividerThickness * separators)

        Column(
            modifier = modifier
                .height(totalHeight)
                .then(tokens.modifier)
        ) {
            groups.forEachIndexed { groupIndex, group ->
                group.forEach { buttonContent ->
                    Box(
                        modifier = Modifier
                            .height(buttonExtent)
                            .padding(tokens.buttonPadding)
                    ) {
                        buttonContent()
                    }
                }

                // insert horizontal divider between groups
                if (groupIndex != groups.lastIndex) {
                    HorizontalDivider(
                        type = DividerType.SECONDARY,
                        horizontalPadding = tokens.dividerHorizontalPadding,
                        verticalPadding = tokens.dividerVerticalPadding,
                        thickness = tokens.dividerThickness
                    )
                }
            }
        }
    }
}
