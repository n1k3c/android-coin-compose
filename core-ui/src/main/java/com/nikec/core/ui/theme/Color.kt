package com.nikec.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFBE2525)
val Green = Color(0xFF51C76A)
val LightGrey = Color(0xFFF8F8F8)
val Grey = Color(0xFF666666)

val Colors.coinHeaderBackground: Color
    @Composable get() = if (isLight) LightGrey else LightGrey

val Colors.coinHeaderText: Color
    @Composable get() = if (isLight) Grey else Grey