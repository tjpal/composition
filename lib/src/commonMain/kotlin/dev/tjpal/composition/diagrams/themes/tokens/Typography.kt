package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.ui.text.TextStyle
import dev.tjpal.composition.diagrams.themes.tokens.TextType

data class Typography(
    val style: TextStyle = TextStyle()
)

data class Typographies(
    val primary: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography(),
    val default: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography(),
    val placeholder: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography(),
    val link: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography()
) {
    fun getTypography(type: dev.tjpal.composition.diagrams.themes.tokens.TextType): dev.tjpal.composition.diagrams.themes.tokens.Typography {
        return when (type) {
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PRIMARY -> primary
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.DEFAULT -> default
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.TextType.PLACEHOLDER -> placeholder
        }
    }
}
