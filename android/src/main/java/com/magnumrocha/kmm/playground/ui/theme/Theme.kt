package com.magnumrocha.kmm.playground.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val ThemeColors = darkColors(
    primary = Color.White,
    secondary = gray700,
    background = grayDarkPrimary,
    surface = grayDarkPrimary
)

private val ThemeElevation = Elevations(card = 4.dp)

@Composable
fun KMMPlaygroundTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalElevations provides ThemeElevation
    ) {
        MaterialTheme(
            colors = ThemeColors,
            typography = typography,
         // shapes = shapes,
            content = content
        )
    }
}
