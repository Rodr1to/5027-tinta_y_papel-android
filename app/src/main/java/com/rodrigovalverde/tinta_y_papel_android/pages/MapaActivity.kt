package com.rodrigovalverde.tinta_y_papel_android.pages

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.rodrigovalverde.tinta_y_papel_android.data.ListaSucursales
import com.rodrigovalverde.tinta_y_papel_android.data.Sucursal
import com.rodrigovalverde.tinta_y_papel_android.ui.theme.Tinta_y_papel_androidTheme

class MapaActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tinta_y_papel_androidTheme {
                val lima = LatLng(-12.046, -77.030)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(lima, 11f)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Mapa de Sucursales") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    GoogleMap(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        cameraPositionState = cameraPositionState
                    ) {
                        ListaSucursales.forEach { sucursal ->
                            val posicion = LatLng(sucursal.latitud, sucursal.longitud)


                            Marker(
                                state = MarkerState(position = posicion),
                                title = sucursal.nombre,
                                snippet = "Clic para ver detalle",
                                onClick = {
                                    irADetalle(sucursal)
                                    true
                                }
                            )


                            Circle(
                                center = posicion,
                                radius = 200.0, // Radio ajustado
                                fillColor = Color(0x220000FF),
                                strokeColor = Color.Blue,
                                strokeWidth = 2f,
                                clickable = true,
                                onClick = {
                                    irADetalle(sucursal)
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    private fun irADetalle(sucursal: Sucursal) {
        val intent = Intent(this, DetalleLugarActivity::class.java)
        intent.putExtra("sucursal_data", sucursal)
        startActivity(intent)
    }
}