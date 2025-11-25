package com.rodrigovalverde.tinta_y_papel_android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rodrigovalverde.tinta_y_papel_android.screen.CategoriasScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.HomeScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.LoginScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.ProfileScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.WelcomeScreen
import com.rodrigovalverde.tinta_y_papel_android.screens.*
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.LoginViewModel
import com.rodrigovalverde.tinta_y_papel_android.screens.LibreriasScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()

    MainScaffold(navController = navController) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.SplashScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreens.SplashScreen.route) {
                SplashScreen(navController = navController, loginViewModel = loginViewModel)
            }
            composable(route = AppScreens.WelcomeScreen.route) {
                WelcomeScreen(navController = navController)
            }
            composable(route = AppScreens.LoginScreen.route) {
                LoginScreen(navController = navController, viewModel = loginViewModel)
            }
            composable(route = AppScreens.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(route = AppScreens.LibreriasScreen.route) {
                LibreriasScreen(navController = navController)
            }
            composable(route = AppScreens.CategoriasScreen.route) {
                CategoriasScreen(navController = navController)
            }
            composable(route = AppScreens.CatalogScreen.route) {
                CatalogScreen(navController = navController)
            }
            composable(route = AppScreens.ProfileScreen.route) {
                ProfileScreen(navController = navController, viewModel = loginViewModel)
            }
            composable(route = AppScreens.SavedScreen.route) {
                SavedScreen(navController = navController)
            }
            composable(
                route = AppScreens.LibrosPorCategoriaScreen.route,
                arguments = listOf(navArgument("idCategoria") { type = NavType.IntType })
            ) {
                LibrosPorCategoriaScreen(navController, it.arguments!!.getInt("idCategoria"))
            }
            composable(
                route = AppScreens.LibroDetalleScreen.route,
                arguments = listOf(navArgument("libroId") { type = NavType.IntType })
            ) {
                LibroDetalleScreen(navController, it.arguments!!.getInt("libroId"))
            }
        }
    }
}