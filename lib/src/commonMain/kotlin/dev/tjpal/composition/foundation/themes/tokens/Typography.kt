package dev.tjpal.composition.foundation.themes.tokens

import androidx.compose.ui.text.TextStyle
import dev.tjpal.composition.foundation.themes.tokens.TextType

data class Typography(
    val style: TextStyle = TextStyle()
)

data class Typographies(
    val primary: Typography = Typography(),
    val default: Typography = Typography(),
    val link: Typography = Typography()
) {
    fun getTypography(type: TextType): Typography {
        return when (type) {
            TextType.PRIMARY -> primary
            TextType.DEFAULT -> default
        }
    }
}
