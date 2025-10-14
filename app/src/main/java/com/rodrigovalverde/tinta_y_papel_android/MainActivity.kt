package com.rodrigovalverde.tinta_y_papel_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppNavigation
import com.rodrigovalverde.tinta_y_papel_android.ui.theme.Tinta_y_papel_androidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Se aplica tu tema personalizado a toda la aplicación.
            // dynamicColor = false asegura que siempre se usen tus colores y no los del fondo de pantalla.
            Tinta_y_papel_androidTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Aquí se llama al sistema de navegación que controla toda la app.
                    AppNavigation()
                }
            }
        }
    }
}