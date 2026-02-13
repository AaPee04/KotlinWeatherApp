package com.example.kotlinweatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val GamerColorScheme = darkColorScheme(
    primary = CrimsonRed,
    secondary = BloodRed,
    tertiary = NeonRed,

    background = GamerBlack,
    surface = GamerSurface,
    surfaceVariant = GamerCard,

    onPrimary = GamerWhite,
    onSecondary = GamerWhite,
    onTertiary = GamerWhite,
    onBackground = GamerWhite,
    onSurface = GamerWhite
)

@Composable
fun KotlinWeatherAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GamerColorScheme,
        typography = Typography,
        content = content
    )
}
