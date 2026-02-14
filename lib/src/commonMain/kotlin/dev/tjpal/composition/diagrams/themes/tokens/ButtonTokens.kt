package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.ui.Modifier

enum class ButtonType {
    PRIMARY,
    DEFAULT,
    SHY
}

enum class TextType {
    PRIMARY,
    DEFAULT,
    PLACEHOLDER
}

enum class DividerType {
    PRIMARY,
    SECONDARY
}

data class ButtonStateTokens(
    val modifier: Modifier = Modifier
)

data class ButtonCategoryTokens(
    val flat: dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(),
    val default: dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(),
    val pressed: dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonStateTokens(),
)

data class ButtonTokens(
    val primaryButton: dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens(),
    val shyButton: dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens(),
    val iconButton: dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens(),
    val primaryText: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography(),
    val defaultText: dev.tjpal.composition.diagrams.themes.tokens.Typography = _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.Typography()
) {
    fun getButtonTheme(type: dev.tjpal.composition.diagrams.themes.tokens.ButtonType): dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens {
        return when (type) {
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.PRIMARY -> primaryButton
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.DEFAULT -> primaryButton
            _root_ide_package_.dev.tjpal.composition.diagrams.themes.tokens.ButtonType.SHY -> shyButton
        }
    }

    fun getIconButtonTheme(type: dev.tjpal.composition.diagrams.themes.tokens.ButtonType): dev.tjpal.composition.diagrams.themes.tokens.ButtonCategoryTokens {
        return iconButton
    }
}
