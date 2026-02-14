package dev.tjpal.composition.diagrams.themes.tokens

import androidx.compose.ui.text.TextStyle
import dev.tjpal.composition.diagrams.themes.tokens.TextType

data class Typography(
    val style: TextStyle = TextStyle()
)

data class Typographies(
    val primary: Typography = Typography(),
    val default: Typography = Typography(),
    val placeholder: Typography = Typography(),
    val link: Typography = Typography()
) {
    fun getTypography(type: TextType): Typography {
        return when (type) {
            TextType.PRIMARY -> primary
            TextType.DEFAULT -> default
            TextType.PLACEHOLDER -> placeholder
        }
    }
}
