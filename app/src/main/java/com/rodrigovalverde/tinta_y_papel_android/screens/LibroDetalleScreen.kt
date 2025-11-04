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
    savedViewModel: SavedViewModel = viewModel() // <-- ViewModel de Room
) {
    val libro = detalleViewModel.libro
    val scope = rememberCoroutineScope()

    // Estado para saber si el libro está guardado
    var isSaved by remember { mutableStateOf(false) }

    // Cargar detalles del libro y verificar si está guardado
    LaunchedEffect(idLibro) {
        detalleViewModel.getLibroDetalle(idLibro)
        // Preguntar al ViewModel si el libro está guardado
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
                // --- ACCIÓN DE GUARDAR ---
                actions = {
                    // Solo mostramos el botón si el libro se ha cargado
                    libro?.let { libroDetalle ->
                        IconButton(onClick = {
                            scope.launch {
                                if (isSaved) {
                                    // Si ya está guardado, lo eliminamos
                                    savedViewModel.eliminarLibro(libroDetalle)
                                } else {
                                    // Si no está guardado, lo guardamos
                                    savedViewModel.guardarLibro(libroDetalle)
                                }
                                isSaved = !isSaved // Invertimos el estado visualmente
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
        if (libro == null) {
            // Muestra un indicador de carga mientras el libro es null
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Cuando el libro se carga, se muestra el contenido
            Column(
                modifier = Modifier
                    .padding(padding)
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
                    val precioDouble = libro.precio
                    DetailItem(label = "Precio", value = "S/ ${"%.2f".format(precioDouble)}")
                    DetailItem(label = "Stock", value = libro.stock.toString())
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
                    text = libro.sinopsis,
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
                DetailItem(label = "Editorial", value = libro.editorial)
                DetailItem(label = "ISBN", value = libro.isbn)
                DetailItem(label = "Publicación", value = libro.fecha_publicacion)

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
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