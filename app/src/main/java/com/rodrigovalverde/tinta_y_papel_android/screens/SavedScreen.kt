package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // <-- IMPORTANTE
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.SavedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    navController: NavController,
    viewModel: SavedViewModel = viewModel() // <-- AÑADIR VIEWMODEL
) {
    // Observar la lista de libros guardados desde el ViewModel
    val librosGuardados by viewModel.librosGuardados.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Libros Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(AppScreens.HomeScreen.route) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a Inicio"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (librosGuardados.isEmpty()) {
            // Mensaje si no hay libros guardados
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Filled.Bookmark,
                    "Guardados",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Aún no has guardado ningún libro.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Presiona el ícono de marcapáginas en la página de un libro para añadirlo aquí.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            // Lista de libros guardados
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(librosGuardados) { libroGuardado ->
                    val libro = Libro(
                        id = libroGuardado.id,
                        titulo = libroGuardado.titulo,
                        autor = libroGuardado.autor,
                        url_portada = libroGuardado.url_portada,
                        precio = libroGuardado.precio,
                        isbn = "", editorial = "", id_categoria = 0,
                        stock = 0, sinopsis = "", fecha_publicacion = ""
                    )
                    // Reutilizamos el Composable de CatalogScreen
                    LibroRowCard(libro = libro, navController = navController)
                }
            }
        }
    }
}