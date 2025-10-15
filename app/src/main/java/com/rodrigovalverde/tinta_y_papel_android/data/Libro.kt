package com.rodrigovalverde.tinta_y_papel_android.data

data class Libro(
    val id: Int,
    val isbn: String,
    val titulo: String,
    val autor: String,
    val editorial: String,
    val id_categoria: Int,
    val precio: String, // CAMBIO CRÍTICO: Se cambia de Double a String
    val stock: Int,
    val sinopsis: String,
    val url_portada: String?,
    val fecha_publicacion: String
)