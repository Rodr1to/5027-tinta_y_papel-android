package com.rodrigovalverde.tinta_y_papel_android.data

import com.google.gson.annotations.SerializedName

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,

    // Hacemos que los campos problemáticos sean opcionales (String?)
    // Si tu API devuelve un Double para precio, usa Double?
    val precio: String?, // <-- Cambiar a String? (o Double?)
    val stock: Int?, // <-- Cambiar a Int?

    // Los demás campos pueden ser opcionales o Strings
    val isbn: String?,
    val editorial: String?,
    @SerializedName("id_categoria")
    val id_categoria: Int?,
    val sinopsis: String?,
    @SerializedName("fecha_publicacion")
    val fecha_publicacion: String?,

    // El nombre de la URL en la API es 'portada_url' o similar.
    @SerializedName("url_portada")
    val url_portada: String?
)