package com.rodrigovalverde.tinta_y_papel_android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Tus paletas de colores (DarkColorScheme y LightColorScheme) se quedan igual
private val DarkColorScheme = darkColorScheme(
    primary = color1,
    onPrimary = White,
    background = color2,
    onBackground = White,
    surface = color2,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = color1,
    onPrimary = White,
    background = LightGray,
    onBackground = color2,
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

    // --- 👇 CAMBIO CRÍTICO AQUÍ 👇 ---
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb() // Opcional: Iguala el color de fondo
            // Le dice al sistema que use íconos oscuros en temas claros
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    // --- FIN DEL CAMBIO ---

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}