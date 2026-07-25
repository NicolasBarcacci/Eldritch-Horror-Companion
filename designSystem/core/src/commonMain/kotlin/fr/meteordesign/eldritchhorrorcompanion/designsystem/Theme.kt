package fr.meteordesign.eldritchhorrorcompanion.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val EhcDarkColorScheme = darkColorScheme(
    primary = EldritchPurple80,
    secondary = EldritchGreen80,
    tertiary = EldritchGold80,
    background = EldritchInk,
    surface = EldritchInk,
    onBackground = EldritchParchment,
    onSurface = EldritchParchment,
)

private val EhcLightColorScheme = lightColorScheme(
    primary = EldritchPurple40,
    secondary = EldritchGreen40,
    tertiary = EldritchGold40,
    background = EldritchParchment,
    surface = EldritchParchment,
    onBackground = EldritchInk,
    onSurface = EldritchInk,
)

@Composable
fun EhcTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) EhcDarkColorScheme else EhcLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = EhcTypography,
        content = content,
    )
}
