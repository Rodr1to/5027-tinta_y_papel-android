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
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rodrigovalverde.tinta_y_papel_android.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem(AppScreens.HomeScreen.route, R.drawable.home, "Inicio")
    object Category : BottomNavItem(AppScreens.CategoriasScreen.route, R.drawable.category, "Categorías")
    object Catalog : BottomNavItem(AppScreens.CatalogScreen.route, R.drawable.catalog, "Catálogo")
    object Profile : BottomNavItem(AuthNav.route, R.drawable.profile, "Perfil")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavController, content: @Composable (Modifier) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Catalog,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            if (currentDestination?.hierarchy?.any { dest -> items.any { it.route == dest.route } } == true) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = screen.icon),
                                    contentDescription = screen.label,
                                    // CAMBIO: Se reduce aún más el tamaño del ícono
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            // CAMBIO: Se reduce aún más el tamaño del texto
                            label = { Text(screen.label, fontSize = 9.sp) },
                            selected = currentDestination.hierarchy.any { it.route == screen.route },
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
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
        content(Modifier.padding(innerPadding))
    }
}