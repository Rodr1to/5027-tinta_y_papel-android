package com.rodrigovalverde.tinta_y_papel_android.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigovalverde.tinta_y_papel_android.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Cambiamos a AndroidViewModel para tener acceso al contexto de la aplicación
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Obtenemos la instancia única del SessionManager
    private val sessionManager = SessionManager.getInstance(application)

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    init {
        // Al iniciar, comprueba si hay una sesión guardada en el dispositivo
        _loggedInUser.value = sessionManager.getSession()
    }

    fun login(user: String, pass: String, rememberSession: Boolean) {
        loginUiState = LoginUiState(isLoading = true)
        viewModelScope.launch {
            try {
                val response = ApiService.getInstance().login(LoginRequest(user, pass))
                if (response.status == "success" && response.user != null) {
                    val userObject = User(response.user.id, response.user.nombres, response.user.apellidos)

                    // 1. Guardamos el usuario en la memoria para la sesión actual SIEMPRE
                    sessionManager.currentUser = userObject
                    _loggedInUser.value = userObject

                    // 2. Si el checkbox está marcado, guardamos la sesión de forma persistente
                    if (rememberSession) {
                        sessionManager.saveSession(userObject.id, userObject.nombres, userObject.apellidos)
                    }

                    loginUiState = LoginUiState(isSuccess = true)
                } else {
                    loginUiState = LoginUiState(error = response.message ?: "Credenciales incorrectas")
                }
            } catch (e: Exception) {
                loginUiState = LoginUiState(error = "Error de conexión: ${e.message}")
            }
        }
    }

    fun logout() {
        // Al cerrar sesión, se limpian tanto la memoria como el almacenamiento persistente
        sessionManager.clearSession()
        _loggedInUser.value = null
    }
}

// La data class LoginUiState se queda igual
data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)