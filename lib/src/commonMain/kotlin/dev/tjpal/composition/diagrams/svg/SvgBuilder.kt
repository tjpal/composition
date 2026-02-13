package dev.tjpal.composition.diagrams.svg

import androidx.compose.ui.graphics.Color
import kotlin.math.max

internal fun Color.toSvgColor(): String {
    val alpha = (this.alpha * 255f).toInt().coerceIn(0, 255)
    val red = (this.red * 255f).toInt().coerceIn(0, 255)
    val green = (this.green * 255f).toInt().coerceIn(0, 255)
    val blue = (this.blue * 255f).toInt().coerceIn(0, 255)

    val redHex = red.toTwoDigitHex()
    val greenHex = green.toTwoDigitHex()
    val blueHex = blue.toTwoDigitHex()

    return if (alpha == 0xFF) {
        "#$redHex$greenHex$blueHex"
    } else {
        val alphaHex = alpha.toTwoDigitHex()
        "#$alphaHex$redHex$greenHex$blueHex"
    }
}

internal fun Int.toTwoDigitHex(): String {
    val clamped = this.coerceIn(0, 255)
    val hex = clamped.toString(16).uppercase()
    return hex.padStart(2, '0')
}

internal class SvgBuilder(width: Float, height: Float) {
    private val builder = StringBuilder()
    private var indentLevel = 1

    init {
        builder.appendLine("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"$width\" height=\"$height\" viewBox=\"0 0 $width $height\">")
    }

    private fun indent(): String = "  ".repeat(indentLevel)

    fun openTag(name: String, attributes: Map<String, String> = emptyMap()) {
        builder.appendLine("${indent()}<$name${attributesToString(attributes)}>")
        indentLevel++
    }

    fun closeTag(name: String) {
        indentLevel = max(0, indentLevel - 1)
        builder.appendLine("${indent()}</$name>")
    }

    fun selfClosingTag(name: String, attributes: Map<String, String> = emptyMap()) {
        builder.appendLine("${indent()}<$name${attributesToString(attributes)} />")
    }

    fun addText(text: String) {
        builder.appendLine("${indent()}$text")
    }

    fun build(): String {
        builder.appendLine("</svg>")
        return builder.toString()
    }

    private fun attributesToString(attributes: Map<String, String>): String {
        if (attributes.isEmpty()) {
            return ""
        }

        return attributes.entries.joinToString(separator = " ", prefix = " ") { "${it.key}=\"${it.value}\"" }
    }
}
