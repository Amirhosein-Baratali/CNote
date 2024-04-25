package com.example.cnote.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        textDirection = TextDirection.Content
    ),
    bodyMedium = Typography().bodyMedium.withContentTextDirection(),
    bodySmall = Typography().bodySmall.withContentTextDirection(),
    headlineLarge = Typography().headlineLarge.withContentTextDirection(),
    headlineMedium = Typography().headlineMedium.withContentTextDirection(),
    headlineSmall = Typography().headlineSmall.withContentTextDirection()

)

fun TextStyle.withContentTextDirection(): TextStyle = this.copy(textDirection = TextDirection.Content)