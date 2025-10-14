package com.rodrigovalverde.tinta_y_papel_android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.rodrigovalverde.tinta_y_papel_android.screen.CatalogScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.CategoriasScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.HomeScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.LoginScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.ProfileScreen
import com.rodrigovalverde.tinta_y_papel_android.screen.WelcomeScreen
import com.rodrigovalverde.tinta_y_papel_android.screens.*
import com.rodrigovalverde.tinta_y_papel_android.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Se crea una única instancia del ViewModel aquí, que se compartirá.
    val loginViewModel: LoginViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Catalog,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavItems.map { it.route }) {
                NavigationBar {
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.icon),
                                    contentDescription = screen.label,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = { Text(screen.label, fontSize = 10.sp) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            alwaysShowLabel = true
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.SplashScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreens.SplashScreen.route) {
                // CAMBIO AQUÍ: Se pasa el loginViewModel al SplashScreen.
                SplashScreen(navController = navController, loginViewModel = loginViewModel)
            }
            composable(route = AppScreens.WelcomeScreen.route) { WelcomeScreen(navController) }
            composable(route = AppScreens.HomeScreen.route) { HomeScreen(navController) }
            composable(route = AppScreens.CategoriasScreen.route) { CategoriasScreen(navController) }
            composable(route = AppScreens.CatalogScreen.route) { CatalogScreen(navController) }
            composable(route = AppScreens.LoginScreen.route) {
                LoginScreen(navController = navController, viewModel = loginViewModel)
            }
            composable(route = AppScreens.ProfileScreen.route) {
                ProfileScreen(navController = navController, viewModel = loginViewModel)
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