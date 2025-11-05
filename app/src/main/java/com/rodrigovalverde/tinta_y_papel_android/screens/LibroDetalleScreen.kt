package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.foundation.Image // <-- 1. IMPORTAR Image
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
import coil.compose.rememberAsyncImagePainter // <-- 2. IMPORTAR EL PAINTER DE COIL 2
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
    // --- 3. CREAR COPIA LOCAL INMUTABLE ---
    // Esto arregla los errores de "nullable receiver"
    val currentLibro = detalleViewModel.libro
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
                title = { Text(currentLibro?.titulo ?: "Cargando...") }, // <-- Usar copia local
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver atrás")
                    }
                },
                actions = {
                    currentLibro?.let { libroDetalle -> // <-- Usar copia local
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
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                errorMessage != null -> {
                    Text(
                        "Error: $errorMessage",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
                // --- 4. USAR COPIA LOCAL EN LA VALIDACIÓN ---
                currentLibro == null -> {
                    Text("Detalles del libro no disponibles.", textAlign = TextAlign.Center)
                }
                else -> {
                    // --- 5. USAR COPIA LOCAL (AHORA NO NULA) EN TODO EL BLOQUE ---
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        // --- 6. CAMBIAR A SINTAXIS COIL 2 ---
                        Image( // <-- Usar Image
                            painter = rememberAsyncImagePainter( // <-- Usar painter
                                model = "https://rovalverde.alwaysdata.net/" + (currentLibro.url_portada ?: "")
                            ),
                            contentDescription = "Portada de ${currentLibro.titulo}", // <-- Ahora es seguro
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(350.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        // --- FIN DEL CAMBIO A COIL 2 ---

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = currentLibro.titulo, // <-- Seguro
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentLibro.autor, // <-- Seguro
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            val precioValue = currentLibro.precio ?: "N/D"
                            val stockValue = currentLibro.stock?.toString() ?: "N/D"

                            DetailItem(label = "Precio", value = "S/ $precioValue")
                            DetailItem(label = "Stock", value = stockValue)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Sinopsis",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentLibro.sinopsis ?: "Sin sinopsis disponible.",
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Detalles Adicionales",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        DetailItem(label = "Editorial", value = currentLibro.editorial ?: "N/D")
                        DetailItem(label = "ISBN", value = currentLibro.isbn ?: "N/D")
                        DetailItem(label = "Publicación", value = currentLibro.fecha_publicacion ?: "N/D")

                        Spacer(modifier = Modifier.height(32.dp))
                    }
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