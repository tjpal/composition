package dev.tjpal.composition.foundation.functional

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.tjpal.composition.foundation.spacing.VerticalDivider
import dev.tjpal.composition.foundation.spacing.HorizontalDivider
import dev.tjpal.composition.diagrams.themes.tokens.DividerType
import dev.tjpal.composition.diagrams.themes.tokens.FloatingBarOrientation
import dev.tjpal.composition.diagrams.themes.tokens.Theme

/**
 * DSL and builder for constructing groups/items for FloatingBar in a declarative way.
 *
 * Usage:
 * FloatingBar(buttonExtent = 64.dp) {
 *   group {
 *     item { /* composable */ }
 *     item { /* composable */ }
 *   }
 *   group { ... }
 * }
 */
@DslMarker
annotation class FloatingBarDsl

@dev.tjpal.composition.foundation.functional.FloatingBarDsl
class FloatingBarBuilder {
    internal val groups = mutableListOf<dev.tjpal.composition.foundation.functional.GroupBuilder>()

    fun group(block: dev.tjpal.composition.foundation.functional.GroupBuilder.() -> Unit) {
        val gb = _root_ide_package_.dev.tjpal.composition.foundation.functional.GroupBuilder().apply(block)
        groups += gb
    }
}

@dev.tjpal.composition.foundation.functional.FloatingBarDsl
class GroupBuilder {
    internal val items = mutableListOf<@Composable () -> Unit>()

    fun item(content: @Composable () -> Unit) {
        items += content
    }
}

@Composable
fun FloatingBar(
    modifier: Modifier = Modifier,
    buttonExtent: Dp,
    orientation: dev.tjpal.composition.diagrams.themes.tokens.FloatingBarOrientation = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FloatingBarOrientation.HORIZONTAL,
    content: dev.tjpal.composition.foundation.functional.FloatingBarBuilder.() -> Unit
) {
    val builder = _root_ide_package_.dev.tjpal.composition.foundation.functional.FloatingBarBuilder().apply(content)
    val groups = builder.groups.map { it.items.toList() }

    if(groups.isEmpty()) {
        return
    }

    val tokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Theme.current.floatingBar
    val totalButtons = groups.sumOf { it.size }
    val separators = (groups.size - 1).coerceAtLeast(0)

    if (orientation == _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FloatingBarOrientation.HORIZONTAL) {
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
                    _root_ide_package_.dev.tjpal.composition.foundation.spacing.VerticalDivider(
                        type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerType.PRIMARY,
                        horizontalPadding = tokens.dividerHorizontalPadding,
                        verticalPadding = tokens.dividerVerticalPadding,
                        thickness = tokens.dividerThickness
                    )
                }
            }
        }
    } else if(orientation == _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.FloatingBarOrientation.VERTICAL) {
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
                    _root_ide_package_.dev.tjpal.composition.foundation.spacing.HorizontalDivider(
                        type = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.DividerType.SECONDARY,
                        horizontalPadding = tokens.dividerHorizontalPadding,
                        verticalPadding = tokens.dividerVerticalPadding,
                        thickness = tokens.dividerThickness
                    )
                }
            }
        }
    }
}
