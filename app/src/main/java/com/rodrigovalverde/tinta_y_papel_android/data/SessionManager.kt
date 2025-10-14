package com.rodrigovalverde.tinta_y_papel_android.data

import android.content.Context
import android.content.SharedPreferences

// El constructor ahora es privado para forzar el uso del singleton
class SessionManager private constructor(context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences("TintaYPapelPrefs", Context.MODE_PRIVATE)

    // Variable para guardar el usuario actual solo en la memoria (sesión temporal)
    var currentUser: User? = null

    companion object {
        const val USER_ID = "user_id"
        const val USER_NOMBRES = "user_nombres"
        const val USER_APELLIDOS = "user_apellidos"

        @Volatile
        private var INSTANCE: SessionManager? = null

        // Método para obtener la única instancia de SessionManager
        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SessionManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    // Guarda la sesión de forma persistente en el dispositivo
    fun saveSession(id: Int, nombres: String, apellidos: String) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, id)
        editor.putString(USER_NOMBRES, nombres)
        editor.putString(USER_APELLIDOS, apellidos)
        editor.apply()
    }

    // Obtiene la sesión persistente del dispositivo
    fun getSession(): User? {
        val id = prefs.getInt(USER_ID, -1)
        if (id == -1) {
            return null
        }
        return User(
            id = id,
            nombres = prefs.getString(USER_NOMBRES, "") ?: "",
            apellidos = prefs.getString(USER_APELLIDOS, "") ?: ""
        ).also {
            // Si cargamos una sesión guardada, también la ponemos en la memoria actual
            currentUser = it
        }
    }

    // Limpia tanto la sesión persistente como la de memoria
    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
        currentUser = null
    }
}