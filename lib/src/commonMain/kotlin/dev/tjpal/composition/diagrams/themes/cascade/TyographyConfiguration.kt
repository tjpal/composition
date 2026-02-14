package dev.tjpal.composition.diagrams.themes.cascade

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import dev.tjpal.composition.diagrams.themes.tokens.Typographies
import dev.tjpal.composition.diagrams.themes.tokens.Typography

fun typographyConfiguration(): Typographies {
    return Typographies(
        primary = Typography(
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                lineHeight = 14.sp,
                baselineShift = BaselineShift.None,
                color = Color(102, 116, 141, 255)
            )
        ),
        default = Typography(
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                lineHeight = 14.sp,
                baselineShift = BaselineShift.None,
                color = Color(102, 116, 141, 255)
            )
        ),
        placeholder = Typography(
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                lineHeight = 14.sp,
                baselineShift = BaselineShift.None,
                color = Color(198, 206, 220, 255)
            )
        ),
        link = Typography(
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                lineHeight = 14.sp,
                baselineShift = BaselineShift.None,
                color = Color(83, 135, 250)
            )
        )
    )
}