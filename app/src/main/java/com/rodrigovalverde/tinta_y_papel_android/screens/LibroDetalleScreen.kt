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
import coil3.compose.AsyncImage // Usamos Coil 3
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
    // --- LEEMOS LOS NUEVOS ESTADOS DEL VIEWMODEL ---
    val libro = detalleViewModel.libro
    val isLoading = detalleViewModel.isLoading
    val errorMessage = detalleViewModel.errorMessage

    val scope = rememberCoroutineScope()
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(idLibro) {
        if (idLibro > 0) {
            detalleViewModel.getLibroDetalle(idLibro)
            isSaved = savedViewModel.isLibroGuardado(idLibro)
        }
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
                    // Botón de Guardado solo si el libro existe y no hay error
                    libro?.let { libroDetalle ->
                        IconButton(onClick = {
                            scope.launch {
                                if (isSaved) {
                                    savedViewModel.eliminarLibro(libroDetalle)
                                } else {
                                    savedViewModel.guardarLibro(libroDetalle)
                                }
                                isSaved = savedViewModel.isLibroGuardado(idLibro)
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
    ) { padding ->
        // --- MANEJO DE ESTADOS DE UI ---
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    // 1. Muestra "Cargando..."
                    CircularProgressIndicator()
                }
                errorMessage != null -> {
                    // 2. Muestra el error de la API
                    Text(
                        "Error: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
                libro == null && !isLoading -> {
                    // 3. Estado por defecto si no carga y no hay error
                    Text("Detalles del libro no disponibles.", textAlign = TextAlign.Center)
                }
                else -> {
                    // 4. Muestra el contenido del libro (TU CÓDIGO ANTERIOR)
                    Column(
                        modifier = Modifier
                            .fillMaxSize() // Asegura que la columna llene el espacio
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally // Centra la imagen
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        // Portada del Libro
                        AsyncImage(
                            model = "https://rovalverde.alwaysdata.net/" + (libro?.url_portada ?: ""),
                            contentDescription = "Portada de ${libro?.titulo}",
                            modifier = Modifier
                                .fillMaxWidth(0.8f) // Reduce el ancho de la imagen
                                .height(350.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Título
                        Text(
                            text = libro?.titulo ?: "Sin Título",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Autor
                        Text(
                            text = libro?.autor ?: "Sin Autor",
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
                            val precioValue = libro?.precio ?: "N/D"
                            val stockValue = libro?.stock?.toString() ?: "N/D"

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
                        Text(
                            text = libro?.sinopsis ?: "Sin sinopsis disponible.",
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
                        DetailItem(label = "Editorial", value = libro?.editorial ?: "N/D")
                        DetailItem(label = "ISBN", value = libro?.isbn ?: "N/D")
                        DetailItem(label = "Publicación", value = libro?.fecha_publicacion ?: "N/D")

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                    // --- FIN Contenido del libro ---
                }
            }
        }
    }
}

// Este Composable se queda igual
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