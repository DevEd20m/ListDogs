package com.konfio.test.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


val LightColors = lightColorScheme(
    background = Background,
    surface = Background,
    onBackground = PrimaryText,
    primary = PrimaryText,
    onPrimary = Color.White,
    onSurface = PrimaryText,
    secondary = SecondaryText,
    onSecondary = Color.White,
)

val DarkColors = darkColorScheme(
    background = PrimaryText,
    onBackground = Background,
    primary = Background,
    onPrimary = PrimaryText,
    secondary = SecondaryText,
    onSecondary = Background,
)

@Composable
fun KonfioTestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}