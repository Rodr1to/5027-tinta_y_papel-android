package com.rodrigovalverde.tinta_y_papel_android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rodrigovalverde.tinta_y_papel_android.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem(AppScreens.HomeScreen.route, R.drawable.home, "Inicio")
    object Category : BottomNavItem(AppScreens.CategoriasScreen.route, R.drawable.category, "Categorías")
    object Catalog : BottomNavItem(AppScreens.CatalogScreen.route, R.drawable.catalog, "Catálogo")
    object Saved : BottomNavItem(AppScreens.SavedScreen.route, R.drawable.bookmarks, "Favoritos")
    object Profile : BottomNavItem(AppScreens.ProfileScreen.route, R.drawable.profile, "Perfil")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    // CAMBIO CRÍTICO: La función 'content' ahora debe aceptar PaddingValues
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Catalog,
        BottomNavItem.Saved,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in items.map { it.route }) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.icon),
                                    contentDescription = screen.label,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = { Text(screen.label, fontSize = 10.sp) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            alwaysShowLabel = true
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Ahora, al llamar a 'content', le pasamos el 'innerPadding' que es del tipo correcto (PaddingValues)
        content(innerPadding)
    }
}