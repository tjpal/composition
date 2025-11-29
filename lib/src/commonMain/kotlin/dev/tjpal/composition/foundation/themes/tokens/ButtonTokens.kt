package dev.tjpal.composition.foundation.themes.tokens

import androidx.compose.ui.Modifier

enum class ButtonType {
    PRIMARY,
    DEFAULT,
    SHY
}

enum class TextType {
    PRIMARY,
    DEFAULT
}

enum class DividerType {
    PRIMARY,
    SECONDARY
}

data class ButtonStateTokens(
    val modifier: Modifier = Modifier
)

data class ButtonCategoryTokens(
    val flat: ButtonStateTokens = ButtonStateTokens(),
    val default: ButtonStateTokens = ButtonStateTokens(),
    val pressed: ButtonStateTokens = ButtonStateTokens(),
)

data class ButtonTokens(
    val primaryButton: ButtonCategoryTokens = ButtonCategoryTokens(),
    val shyButton: ButtonCategoryTokens = ButtonCategoryTokens(),
    val iconButton: ButtonCategoryTokens = ButtonCategoryTokens(),
    val primaryText: Typography = Typography(),
    val defaultText: Typography = Typography()
) {
    fun getButtonTheme(type: ButtonType): ButtonCategoryTokens {
        return when (type) {
            ButtonType.PRIMARY -> primaryButton
            ButtonType.DEFAULT -> primaryButton
            ButtonType.SHY -> shyButton
        }
    }

    fun getIconButtonTheme(type: ButtonType): ButtonCategoryTokens {
        return iconButton
    }
}
