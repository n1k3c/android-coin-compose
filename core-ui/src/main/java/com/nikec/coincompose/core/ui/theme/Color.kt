package com.nikec.coincompose.core.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFBE2525)
val Green = Color(0xFF51C76A)
val LightGrey = Color(0xFFF8F8F8)
val LightBlack = Color(0xFF0E0E0E)

val Colors.text: Color
    @Composable get() = if (isLight) Black else White

val Colors.coinHeaderBackground: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.coinHeaderText: Color
    @Composable get() = if (isLight) LightBlack else White

val Colors.dividerHeader: Color
    @Composable get() = if (isLight) White else Black

val Colors.divider: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.fab: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.fabContent: Color
    @Composable get() = if (isLight) Black else White

// Sparkline
val Colors.graphLineColor: Color
    @Composable get() = if (isLight) Black else White

val Colors.axisGridStrokeColor: Color
    @Composable get() = if (isLight) LightGrey else LightBlack

val Colors.priceChangeNegative: Color
    @Composable get() = if (isLight) Red else Red

val Colors.priceChangePositive: Color
    @Composable get() = if (isLight) Green else Green