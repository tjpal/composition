package dev.tjpal.composition.diagrams.scatter.utilities

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round

internal fun formatFixed(value: Float, decimals: Int): String {
    val factor = 10.0.pow(decimals).toFloat()
    val rounded = round(value * factor) / factor

    var result = rounded.toString()

    if (result.contains('.')) {
        result = result.trimEnd('0').trimEnd('.')
    }

    return result
}

internal fun formatTick(value: Float): String {
    val abs = abs(value)
    return when {
        // scientific notation for very large or very small values
        (abs >= 1000f || (abs > 0f && abs < 0.01f)) -> {
            val exp = floor(log10(abs)).toInt()
            val mantissa = value / 10.0.pow(exp).toFloat()

            "${formatFixed(mantissa, 2)}e${exp}"
        }
        // Integer values
        abs(value - value.toLong()) < 0.001f -> value.toLong().toString()
        // Large values with one decimal
        abs >= 10f -> formatFixed(value, 1)
        // Fallback
        else -> formatFixed(value, 2)
    }
}
