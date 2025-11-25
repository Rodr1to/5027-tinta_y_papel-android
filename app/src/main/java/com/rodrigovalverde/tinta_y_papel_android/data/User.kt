package com.rodrigovalverde.tinta_y_papel_android.data

// Modelo para la respuesta completa de la API
data class UserApiResponse(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val telefono: String
)

// Modelo simplificado para guardar en sesión
data class User(
    val id: Int,
    val nombres: String,
    val apellidos: String
)

// Modelo para la respuesta del login
data class LoginResponse(
    val status: String,
    val message: String?,
    val user: UserApiResponse?
)
