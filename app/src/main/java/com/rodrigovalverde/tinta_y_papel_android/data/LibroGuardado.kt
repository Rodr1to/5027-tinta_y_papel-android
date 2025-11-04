package com.rodrigovalverde.tinta_y_papel_android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros_guardados")
data class LibroGuardado(
    @PrimaryKey // Usaremos el ID del libro de la API, que es único
    val id: Int,
    val titulo: String,
    val autor: String,
    val url_portada: String? // Opcional por si alguna URL falla
)