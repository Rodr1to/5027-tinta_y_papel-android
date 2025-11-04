package com.rodrigovalverde.tinta_y_papel_android.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigovalverde.tinta_y_papel_android.data.AppDatabase
import com.rodrigovalverde.tinta_y_papel_android.data.Libro
import com.rodrigovalverde.tinta_y_papel_android.data.LibroGuardado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Usamos AndroidViewModel para tener acceso al Contexto de la aplicación
class SavedViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).libroGuardadoDao()

    // Expone la lista de libros guardados como un StateFlow
    val librosGuardados: StateFlow<List<LibroGuardado>> = dao.getLibrosGuardados()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Función para guardar un libro (convierte Libro de API a LibroGuardado)
    fun guardarLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) { // Usamos IO para operaciones de BD
            val libroGuardado = LibroGuardado(
                id = libro.id,
                titulo = libro.titulo,
                autor = libro.autor,
                url_portada = libro.url_portada
            )
            dao.insert(libroGuardado)
        }
    }

    // Función para eliminar un libro
    fun eliminarLibro(libro: LibroGuardado) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(libro)
        }
    }

    // Función para eliminar un libro usando el objeto 'Libro' de la API
    fun eliminarLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            val libroGuardado = LibroGuardado(
                id = libro.id,
                titulo = libro.titulo,
                autor = libro.autor,
                url_portada = libro.url_portada
            )
            dao.delete(libroGuardado)
        }
    }

    // Función para verificar si un libro ya está guardado
    suspend fun isLibroGuardado(idLibro: Int): Boolean {
        // Ejecutamos en IO y devolvemos el resultado
        return kotlinx.coroutines.withContext(Dispatchers.IO) {
            dao.getLibroById(idLibro) != null
        }
    }
}