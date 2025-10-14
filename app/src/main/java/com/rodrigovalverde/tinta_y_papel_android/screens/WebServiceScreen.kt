package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodrigovalverde.tinta_y_papel_android.viewmodels.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebServiceScreen(navController: NavController, librosViewModel: LibrosViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        librosViewModel.getLibrosList()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Catálogo de nuestros libros") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = librosViewModel.librosList) { libro ->
                // Ahora llama a la función centralizada desde CommonComponents.kt
                LibroCard(libro = libro)
            }
        }
    }
}