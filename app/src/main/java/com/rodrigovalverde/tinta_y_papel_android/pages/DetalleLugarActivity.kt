package com.rodrigovalverde.tinta_y_papel_android.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rodrigovalverde.tinta_y_papel_android.data.Sucursal
import com.rodrigovalverde.tinta_y_papel_android.ui.theme.Tinta_y_papel_androidTheme

class DetalleLugarActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sucursal = intent.getSerializableExtra("sucursal_data") as? Sucursal

        setContent {
            Tinta_y_papel_androidTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Detalle de Sucursal") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                                }
                            }
                        )
                    }
                ) { padding ->
                    if (sucursal != null) {
                        DetalleScreenContent(sucursal, Modifier.padding(padding))
                    } else {
                        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                            Text("No se encontraron datos.")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetalleScreenContent(sucursal: Sucursal, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Image(
                painter = painterResource(id = sucursal.imagenRes), // Usamos el ID de recurso
                contentDescription = sucursal.nombre,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = sucursal.nombre,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Descripción:", style = MaterialTheme.typography.titleMedium)

        Text(
            text = sucursal.descripcion,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}