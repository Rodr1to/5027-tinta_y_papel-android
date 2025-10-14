package com.rodrigovalverde.tinta_y_papel_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigovalverde.tinta_y_papel_android.data.ApiService
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import kotlinx.coroutines.launch

class LibrosViewModel : ViewModel() {
    var librosList: List<Libro> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getLibrosList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                librosList = apiService.getLibros()
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getLibrosPorCategoria(idCategoria: Int) {
        viewModelScope.launch {
            try {
                librosList = ApiService.getInstance().getLibrosPorCategoria(idCategoria)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

}