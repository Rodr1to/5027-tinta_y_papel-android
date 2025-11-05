package com.rodrigovalverde.tinta_y_papel_android.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import com.rodrigovalverde.tinta_y_papel_android.navigation.AppScreens

// Se mueve LibroCard aquí para que sea reutilizable
@Composable
fun LibroCard(libro: Libro) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://rovalverde.alwaysdata.net/" + libro.url_portada),
                contentDescription = "Portada del libro ${libro.titulo}",
                modifier = Modifier.size(80.dp, 120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = libro.titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = libro.autor, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// Se mueve LibroRowCard aquí y se corrige el manejo del precio
@Composable
fun LibroRowCard(libro: Libro, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(AppScreens.LibroDetalleScreen.createRoute(libro.id))
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.height(150.dp)) {
            if (libro.url_portada != null) {
                AsyncImage(
                    model = "https://rovalverde.alwaysdata.net/" + libro.url_portada,
                    contentDescription = libro.titulo,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxHeight().width(100.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = libro.titulo,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = libro.autor,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                // CAMBIO: Se convierte el String a Double de forma segura antes de formatear
                val precioDouble = libro.precio?.toDoubleOrNull() ?: 0.0
                Text(
                    text = "S/ ${"%.2f".format(precioDouble)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}