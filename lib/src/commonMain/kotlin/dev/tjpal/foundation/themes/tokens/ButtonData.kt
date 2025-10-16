package dev.tjpal.foundation.themes.tokens

import androidx.compose.ui.Modifier

data class ButtonStateTheme(
    val modifier: Modifier = Modifier
)

data class ButtonTheme(
    val default: ButtonStateTheme = ButtonStateTheme(),
    val pressed: ButtonStateTheme = ButtonStateTheme(),
)

data class ButtonThemes(
    val primaryButton: ButtonTheme = ButtonTheme(),
    val primaryText: Typography = Typography(),
    val defaultText: Typography = Typography()
) {
    fun getButtonTheme(type: ButtonType): ButtonTheme {
        return when (type) {
            ButtonType.PRIMARY -> primaryButton
            ButtonType.DEFAULT -> primaryButton
        }
    }
}
