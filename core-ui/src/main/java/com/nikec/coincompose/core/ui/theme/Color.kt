package com.nikec.coincompose.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFBE2525)
val Green = Color(0xFF51C76A)
val LightGrey = Color(0xFFF8F8F8)
val Grey = Color(0xFF666666)

val Colors.text: Color
    @Composable get() = if (isLight) Black else White

val Colors.coinHeaderBackground: Color
    @Composable get() = if (isLight) LightGrey else Grey

val Colors.coinHeaderText: Color
    @Composable get() = if (isLight) Grey else White

val Colors.dividerHeader: Color
    @Composable get() = if (isLight) White else White

val Colors.divider: Color
    @Composable get() = if (isLight) LightGrey else LightGrey

val Colors.fab: Color
    @Composable get() = if (isLight) LightGrey else Grey

val Colors.fabContent: Color
    @Composable get() = if (isLight) Black else White

// Sparkline
val Colors.graphLineColor: Color
    @Composable get() = if (isLight) Black else White

val Colors.axisGridStrokeColor: Color
    @Composable get() = if (isLight) LightGrey else Grey

val Colors.priceChangeNegative: Color
    @Composable get() = if (isLight) Red else Red

val Colors.priceChangePositive: Color
    @Composable get() = if (isLight) Green else Green