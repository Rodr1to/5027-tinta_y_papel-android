package com.rodrigovalverde.tinta_y_papel_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigovalverde.tinta_y_papel_android.data.ApiService
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import kotlinx.coroutines.launch

class LibroDetalleViewModel : ViewModel() {
    var libro: Libro? by mutableStateOf(null)
    var errorMessage: String by mutableStateOf("")

    fun getLibroDetalle(idLibro: Int) {
        viewModelScope.launch {
            try {
                // La API ahora devuelve una lista de libros.
                val listaDeUnLibro = ApiService.getInstance().getLibroDetalle(idLibro)

                // --- 👇 CAMBIO CRÍTICO AQUÍ 👇 ---
                // Extraemos el primer elemento de la lista de forma segura.
                libro = listaDeUnLibro.firstOrNull()

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}