package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.LibroDetalleViewModel
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.SavedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroDetalleScreen(
    navController: NavController,
    idLibro: Int,
    detalleViewModel: LibroDetalleViewModel = viewModel(),
    savedViewModel: SavedViewModel = viewModel()
) {
    val libro = detalleViewModel.libro
    val scope = rememberCoroutineScope()

    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(idLibro) {
        detalleViewModel.getLibroDetalle(idLibro)
        isSaved = savedViewModel.isLibroGuardado(idLibro)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(libro?.titulo ?: "Cargando...") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                },
                actions = {
                    libro?.let { libroDetalle ->
                        IconButton(onClick = {
                            scope.launch {
                                if (isSaved) {
                                    savedViewModel.eliminarLibro(libroDetalle)
                                } else {
                                    savedViewModel.guardarLibro(libroDetalle)
                                }
                                isSaved = !isSaved
                            }
                        }) {
                            Icon(
                                imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Guardar",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { padding -> // <-- INICIO DEL CONTENIDO PRINCIPAL (usando el padding)
        if (libro == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding) // <-- APLICACIÓN DEL PADDING DEL SCAFFOLD
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // Portada del Libro
                AsyncImage(
                    model = "https://rovalverde.alwaysdata.net/" + libro.url_portada,
                    contentDescription = "Portada de ${libro.titulo}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Título
                Text(
                    text = libro.titulo,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Autor
                Text(
                    text = libro.autor,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Detalles en Fila (Precio y Stock)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Manejamos los campos que pueden ser null o String
                    val precioValue = libro.precio ?: "N/D"
                    val stockValue = libro.stock?.toString() ?: "N/D" // Manejamos Int?

                    DetailItem(label = "Precio", value = "S/ $precioValue")
                    DetailItem(label = "Stock", value = stockValue)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sinopsis
                Text(
                    text = "Sinopsis",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Manejamos sinopsis nula
                Text(
                    text = libro.sinopsis ?: "Sin sinopsis disponible.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Otros Detalles
                Text(
                    text = "Detalles Adicionales",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                DetailItem(label = "Editorial", value = libro.editorial ?: "N/D")
                DetailItem(label = "ISBN", value = libro.isbn ?: "N/D")
                DetailItem(label = "Publicación", value = libro.fecha_publicacion ?: "N/D")

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    } // <-- FIN DEL SCAFFOLD
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}