package com.rodrigovalverde.tinta_y_papel_android.screen

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rodrigovalverde.tinta_y_papel_android.data.Categoria
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.CategoriasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriasScreen(
    navController: NavController,
    viewModel: CategoriasViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getCategoriasList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorar por categoría") },
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
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(viewModel.categoriasList) { categoria ->
                CategoriaCard(categoria = categoria, navController = navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CategoriaCard(categoria: Categoria, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route = AppScreens.LibrosPorCategoriaScreen.createRoute(categoria.id_categoria))
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.height(150.dp)) {
            AsyncImage(
                model = "https://rovalverde.alwaysdata.net/" + categoria.imagen_url,
                contentDescription = categoria.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 100f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = categoria.nombre,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }
    }
}