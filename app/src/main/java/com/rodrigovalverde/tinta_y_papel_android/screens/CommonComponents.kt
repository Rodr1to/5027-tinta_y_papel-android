package com.rodrigovalverde.tinta_y_papel_android.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.rodrigovalverde.tinta_y_papel_android.data.Libro

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