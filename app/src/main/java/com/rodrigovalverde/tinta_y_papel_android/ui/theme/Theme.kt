package com.rodrigovalverde.tinta_y_papel_android.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Esquema para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = color3,       // Usamos un tono más claro para que resalte en fondo oscuro
    onPrimary = color2,     // Texto oscuro sobre el botón claro
    background = color2,    // Fondo oscuro
    onBackground = White,   // Texto blanco sobre fondo oscuro
    surface = color2,
    onSurface = White
)

// Esquema para el tema claro (el que estás usando principalmente)
private val LightColorScheme = lightColorScheme(
    primary = color1,       // El color principal para tus botones
    onPrimary = White,      // Texto blanco sobre los botones
    background = LightGray, // Fondo claro
    onBackground = color2,  // Texto oscuro sobre fondo claro
    surface = LightGray,
    onSurface = color2
)

@Composable
fun Tinta_y_papel_androidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}