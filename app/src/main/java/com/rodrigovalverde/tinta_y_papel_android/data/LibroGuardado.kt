package com.rodrigovalverde.tinta_y_papel_android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros_guardados")
data class LibroGuardado(
    @PrimaryKey
    val id: Int,
    val titulo: String,
    val autor: String,
    val url_portada: String?,
    val precio: String?
)