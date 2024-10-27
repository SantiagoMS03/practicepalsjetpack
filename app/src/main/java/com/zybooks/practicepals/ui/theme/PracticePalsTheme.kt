package com.zybooks.practicepals.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Purple500,
    onPrimary = White,
    primaryContainer = Purple200,
    onPrimaryContainer = Black,
    secondary = Teal200,
    onSecondary = Black,
    background = Color.White,
    surface = Color.White,
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple200,
    onPrimary = Black,
    primaryContainer = Purple700,
    onPrimaryContainer = White,
    secondary = Teal200,
    onSecondary = Black,
    background = Color.Black,
    surface = Color.Black,
)

@Composable
fun PracticePalsTheme(
    darkTheme: Boolean = false, // Set this based on system or user preference
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}
