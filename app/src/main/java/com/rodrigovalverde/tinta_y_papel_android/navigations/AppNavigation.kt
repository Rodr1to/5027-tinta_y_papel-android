package com.rodrigovalverde.tinta_y_papel_android.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.rodrigovalverde.tinta_y_papel_android.navigation.MainScaffold
import com.rodrigovalverde.tinta_y_papel_android.screens.*
import com.rodrigovalverde.tinta_y_papel_android.viewmodels.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    MainScaffold(navController = navController) { modifier ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.SplashScreen.route,
            modifier = modifier
        ) {
            // --- Pantallas fuera del flujo principal ---
            composable(route = AppScreens.SplashScreen.route) { SplashScreen(navController) }
            composable(route = AppScreens.WelcomeScreen.route) { WelcomeScreen(navController) }

            composable(
                route = AppScreens.LibrosPorCategoriaScreen.route,
                // ... (argumentos se quedan igual)
            ) { /* ... */ }

            composable(
                route = AppScreens.LibroDetalleScreen.route,
                // ... (argumentos se quedan igual)
            ) { /* ... */ }

            // --- Pantallas de la barra de navegación ---
            composable(route = AppScreens.HomeScreen.route) { HomeScreen(navController) }
            composable(route = AppScreens.CategoriasScreen.route) { CategoriasScreen(navController) }
            composable(route = AppScreens.CatalogScreen.route) { CatalogScreen(navController) }

            // --- GRAFO DE AUTENTICACIÓN ANIDADO ---
            // Todas las pantallas aquí dentro compartirán el mismo LoginViewModel
            navigation(
                startDestination = AuthNav.profile,
                route = AuthNav.route
            ) {
                composable(AuthNav.profile) {
                    val viewModel: LoginViewModel = viewModel(
                        navController.getBackStackEntry(AuthNav.route)
                    )
                    ProfileScreen(navController = navController, viewModel = viewModel)
                }
                composable(AuthNav.login) {
                    val viewModel: LoginViewModel = viewModel(
                        navController.getBackStackEntry(AuthNav.route)
                    )
                    LoginScreen(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}