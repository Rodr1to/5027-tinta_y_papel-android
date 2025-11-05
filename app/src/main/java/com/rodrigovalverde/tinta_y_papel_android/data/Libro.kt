package com.rodrigovalverde.tinta_y_papel_android.data

import com.google.gson.annotations.SerializedName

data class Libro(
    val id: Int, // <-- El ID no debe ser nulo para la navegación
    val titulo: String,
    val autor: String,

    // Hacemos que los campos problemáticos sean opcionales
    val precio: String?,
    val stock: Int?,

    // Los demás campos
    val isbn: String?,
    val editorial: String?,
    @SerializedName("id_categoria")
    val id_categoria: Int?,
    val sinopsis: String?,
    @SerializedName("fecha_publicacion")
    val fecha_publicacion: String?,

    @SerializedName("url_portada")
    val url_portada: String?
)