package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeBackground
import dev.tjpal.composition.diagrams.themes.cascade.defaultCascadeShapeShadow
import dev.tjpal.composition.diagrams.themes.cascade.defaultInsetShapeShadow
import dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens
import dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens
import dev.tjpal.composition.diagrams.themes.tokens.ButtonTokens

private val ButtonPadding = PaddingValues(24.dp, 10.dp, 24.dp, 10.dp)
private val IconButtonSize = 48.dp

fun buttonConfiguration(): dev.tjpal.composition.diagrams.themes.tokens.ButtonTokens {
    return _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonTokens(
        primaryButton = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens(
            default = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = Modifier.Companion.defaultCascadeShapeShadow().defaultCascadeBackground()
                    .padding(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.ButtonPadding)
            ),
            pressed = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = Modifier.Companion.defaultInsetShapeShadow().defaultCascadeBackground()
                    .padding(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.ButtonPadding)
            )
        ),
        // A shy button uses flat design with no shadow until the cursor is moved over it. The goal is to reduce visual noise.
        shyButton = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens(
            flat = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = _root_ide_package_.androidx.compose.ui.Modifier.Companion.defaultCascadeBackground()
                    .padding(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.ButtonPadding)
            ),
            default = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = _root_ide_package_.androidx.compose.ui.Modifier.Companion.defaultCascadeShapeShadow()
                    .defaultCascadeBackground()
                    .padding(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.ButtonPadding)
            ),
            pressed = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = _root_ide_package_.androidx.compose.ui.Modifier.Companion.defaultInsetShapeShadow()
                    .defaultCascadeBackground()
                    .padding(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.ButtonPadding)
            )
        ),
        // Icon buttons are visually compact and provide a small visual size.
        iconButton = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens(
            flat = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = _root_ide_package_.androidx.compose.ui.Modifier.Companion.defaultCascadeBackground()
                    .size(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.IconButtonSize)
            ),
            default = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = _root_ide_package_.androidx.compose.ui.Modifier.Companion.defaultCascadeShapeShadow()
                    .defaultCascadeBackground()
                    .size(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.IconButtonSize)
            ),
            pressed = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(
                modifier = _root_ide_package_.androidx.compose.ui.Modifier.Companion.defaultInsetShapeShadow()
                    .defaultCascadeBackground()
                    .size(_root_ide_package_.dev.tjpal.composition.diagrams.themes.cascade.IconButtonSize)
            )
        )
    )
}
