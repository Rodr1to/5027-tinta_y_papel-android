package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a Categorías"
                        )
                    }
                },
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
                // Llama al componente centralizado
                LibroRowCard(libro = libro, navController = navController)
            }
        }
    }
}

// YA NO SE NECESITA LA DEFINICIÓN DE LibroRowCard AQUÍ PORQUE SE MOVIÓ A CommonComponents.kt