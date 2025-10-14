package com.rodrigovalverde.tinta_y_papel_android.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.LibroDetalleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroDetalleScreen(
    navController: NavController,
    idLibro: Int,
    viewModel: LibroDetalleViewModel = viewModel()
) {
    LaunchedEffect(idLibro) {
        viewModel.getLibroDetalle(idLibro)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles del libro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                }
            )
        }
    ) { padding ->
        val libro = viewModel.libro
        if (libro != null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                // Contenedor de la Imagen
                AsyncImage(
                    model = "https://rovalverde.alwaysdata.net/" + libro.url_portada,
                    contentDescription = libro.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Título y Autor
                Text(libro.titulo, style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "por ${libro.autor}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Precio
                Text(
                    text = "S/ ${"%.2f".format(libro.precio)}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
                // Sinopsis
                Text("Sinopsis", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(libro.sinopsis, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(24.dp))
                // Detalles Adicionales
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Detalles del producto", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        DetailItem(title = "Editorial", value = libro.editorial)
                        DetailItem(title = "ISBN", value = libro.isbn)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        DetailItem(title = "Publicación", value = libro.fecha_publicacion)
                        DetailItem(title = "Stock", value = "${libro.stock} unidades")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun DetailItem(title: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}