package com.rodrigovalverde.tinta_y_papel_android.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrosPorCategoriaScreen(
    navController: NavController,
    idCategoria: Int,
    viewModel: LibrosViewModel = viewModel()
) {
    LaunchedEffect(idCategoria) {
        viewModel.getLibrosPorCategoria(idCategoria)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Libros") },
                // CAMBIO: Se añade el ícono de navegación para volver atrás
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a Categorías"
                        )
                    }
                },
                // CAMBIO: Se añaden los colores a la barra superior
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.librosList) { libro ->
                LibroRowCard(libro = libro, navController = navController)
            }
        }
    }
}

@Composable
fun LibroRowCard(libro: Libro, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(AppScreens.LibroDetalleScreen.createRoute(libro.id))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.height(150.dp)) {
            AsyncImage(
                model = "https://rovalverde.alwaysdata.net/" + libro.url_portada,
                contentDescription = libro.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    libro.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    libro.autor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}