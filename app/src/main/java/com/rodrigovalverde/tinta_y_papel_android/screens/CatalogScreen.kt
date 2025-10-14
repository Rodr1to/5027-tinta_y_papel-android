package com.rodrigovalverde.tinta_y_papel_android.screen

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
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens
import com.rodrigovalverde.tinta_y_papel_android.screens.LibroRowCard
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: LibrosViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getLibrosList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo Completo") },
                // CAMBIO: Se añade el ícono de navegación (flecha)
                navigationIcon = {
                    IconButton(onClick = {
                        // Navega a la pantalla de inicio
                        navController.navigate(AppScreens.HomeScreen.route) {
                            // Limpia el historial de navegación para evitar bucles
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a Inicio"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary // Asegura que la flecha sea blanca
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