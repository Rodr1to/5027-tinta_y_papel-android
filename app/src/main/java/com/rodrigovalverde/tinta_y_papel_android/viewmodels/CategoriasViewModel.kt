package com.rodrigovalverde.tinta_y_papel_android.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigovalverde.tinta_y_papel_android.data.ApiService
import com.rodrigovalverde.tinta_y_papel_android.data.Categoria
import kotlinx.coroutines.launch

class CategoriasViewModel : ViewModel() {
    var categoriasList: List<Categoria> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getCategoriasList() {
        viewModelScope.launch {
            try {
                categoriasList = ApiService.getInstance().getCategorias()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}