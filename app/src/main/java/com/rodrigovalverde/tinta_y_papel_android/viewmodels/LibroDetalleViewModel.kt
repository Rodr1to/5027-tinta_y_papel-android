package com.rodrigovalverde.tinta_y_papel_android.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigovalverde.tinta_y_papel_android.data.ApiService
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import kotlinx.coroutines.launch
import java.io.IOException // Necesario para errores de red

class LibroDetalleViewModel : ViewModel() {

    // El estado del libro que se muestra en la UI
    var libro: Libro? by mutableStateOf(null)
        private set

    // Estado para indicar si estamos cargando
    var isLoading by mutableStateOf(false)
        private set

    // Estado para mensajes de error
    var errorMessage: String? by mutableStateOf(null)
        private set; // (;) para que coincida con tu repo

    /**
     * Llama a la API para obtener los detalles de un libro por su ID.
     */
    fun getLibroDetalle(idLibro: Int) {
        // No cargamos si el ID es 0 o negativo (protección extra)
        if (idLibro <= 0) {
            errorMessage = "ID de libro inválido."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            // --- BLOQUE TRY-CATCH PARA MANEJO ROBUSTO DE ERRORES ---
            try {
                // Llamada síncrona a la API
                val apiService = ApiService.getInstance()
                libro = apiService.getLibroDetalle(idLibro)
                Log.d("LibroDetalle", "Libro cargado: ${libro?.titulo}")

            } catch (e: IOException) {
                // Error de red (No hay internet, host inaccesible)
                errorMessage = "Error de red. Verifique su conexión o URL."
                Log.e("LibroDetalle", "Error de red: $e")
                libro = null
            } catch (e: Exception) {
                // Error de la API o del parser (e.g., JSON malformado o 404)
                errorMessage = "Error al cargar el detalle: ${e.message}"
                Log.e("LibroDetalle", "Error general: $e")
                libro = null
            } finally {
                isLoading = false
            }
            // --- FIN DEL BLOQUE TRY-CATCH ---
        }
    }
}