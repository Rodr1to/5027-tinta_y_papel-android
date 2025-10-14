package com.rodrigovalverde.tinta_y_papel_android.navigation

// Objeto para las rutas del grafo de autenticación
object AuthNav {
    const val route = "auth_graph"
    const val profile = "profile_screen"
    const val login = "login_screen"
}

// Mapa general de todas las pantallas de la app
sealed class AppScreens(val route: String) {
    object SplashScreen : AppScreens("splash_screen")
    object WelcomeScreen : AppScreens("welcome_screen")
    object HomeScreen : AppScreens("home_screen")
    object CategoriasScreen : AppScreens("categorias_screen")
    object CatalogScreen : AppScreens("catalog_screen")

    // Rutas que ahora pertenecen al grafo de autenticación
    object ProfileScreen : AppScreens(AuthNav.profile)
    object LoginScreen : AppScreens(AuthNav.login)

    object LibrosPorCategoriaScreen : AppScreens("libros_por_categoria_screen/{idCategoria}") {
        fun createRoute(idCategoria: Int) = "libros_por_categoria_screen/$idCategoria"
    }

    object LibroDetalleScreen : AppScreens("libro_detalle_screen/{libroId}") {
        fun createRoute(libroId: Int) = "libro_detalle_screen/$libroId"
    }
}