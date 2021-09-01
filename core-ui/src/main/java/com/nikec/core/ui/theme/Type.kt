package com.nikec.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    defaultFontFamily = FontFamily.Monospace,
    h1 = TextStyle(
        fontSize = 30.sp,
        color = Black
    ),
    h2 = TextStyle(
        fontSize = 26.sp,
        color = Black
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Black
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Black
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = Black
    )
)
