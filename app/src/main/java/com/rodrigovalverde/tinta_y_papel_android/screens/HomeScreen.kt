package com.rodrigovalverde.tinta_y_papel_android.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodrigovalverde.tinta_y_papel_android.R
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inicio") },
                // CAMBIO: Se añaden los colores a la barra superior
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Novedades en Tinta y Papel",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            NewsCard()

            Button(
                onClick = { navController.navigate(AppScreens.LibreriasScreen.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Sucursales y Mapa")
            }
        }
    }
}

@Composable
fun NewsCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.generated), // Tu imagen
                contentDescription = "Noticia principal",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // <--- AGREGA ESTO: Altura fija para que no sea gigante
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop // <--- CAMBIA ESTO: 'Crop' para que llene el espacio sin deformarse
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "La mejor plataforma para amantes de la lectura",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Explora cursos online de los mejores autores, únete a nuestra comunidad y descubre tu próxima gran aventura literaria. ¡Empezar ahora es más fácil que nunca!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}