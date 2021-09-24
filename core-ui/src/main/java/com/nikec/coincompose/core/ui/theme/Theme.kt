package com.nikec.coincompose.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = LightBlack,
    primaryVariant = LightBlack,
    secondary = White,
    background = Black,
    surface = LightBlack,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White
)

private val LightColorPalette = lightColors(
    primary = LightGrey,
    primaryVariant = LightBlack,
    secondary = Black,
    background = White,
    surface = LightGrey,
    onPrimary = LightBlack,
    onSecondary = Black,
    onBackground = Black,
    onSurface = LightBlack
)

@Composable
fun CoinComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
