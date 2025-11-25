package com.rodrigovalverde.tinta_y_papel_android.data

import com.rodrigovalverde.tinta_y_papel_android.R
import java.io.Serializable


data class Sucursal(
    val id: Int,
    val nombre: String,
    val latitud: Double,
    val longitud: Double,
    val descripcion: String,
    val imagenRes: Int
) : Serializable

val ListaSucursales = listOf(
    Sucursal(
        1,
        "Sede Central - Miraflores",
        -12.115, -77.030,
        "Nuestra sede principal con cafetería y zona de lectura.",
        R.drawable.sede_miraflores
    ),
    Sucursal(
        2,
        "Sucursal Lince",
        -12.086, -77.038,
        "Especializada en libros de arte y diseño.",
        R.drawable.sede_lince
    ),
    Sucursal(
        3,
        "Librería Centro",
        -12.046, -77.030,
        "Ubicada en el corazón histórico de Lima.",
        R.drawable.sede_centro
    ),
    Sucursal(
        4,
        "Punto San Isidro",
        -12.095, -77.035,
        "Especializada en libros técnicos y de negocios.",
        R.drawable.sede_sanisidro
    ),
    Sucursal(
        5,
        "Surco - El Polo",
        -12.130, -76.980,
        "Ambiente familiar y zona para niños.",
        R.drawable.sede_surco
    ),
    Sucursal(
        6,
        "Punto Barranco",
        -12.143, -77.021,
        "Focus en literatura contemporánea y poesía.",
        R.drawable.sede_barranco
    )
)