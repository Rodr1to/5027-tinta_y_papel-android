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
import kotlinx.coroutines.withContext

class SavedViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).libroGuardadoDao()

    val librosGuardados: StateFlow<List<LibroGuardado>> = dao.getLibrosGuardados()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // CORRECCIÓN: Aseguramos que se guarde el precio
    fun guardarLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            val libroGuardado = LibroGuardado(
                id = libro.id,
                titulo = libro.titulo,
                autor = libro.autor,
                url_portada = libro.url_portada,
                precio = libro.precio // <-- Guardando el precio
            )
            dao.insert(libroGuardado)
        }
    }

    // CORRECCIÓN: Aseguramos que se use el objeto LibroGuardado correcto para eliminar
    fun eliminarLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            val libroGuardado = LibroGuardado(
                id = libro.id,
                titulo = libro.titulo,
                autor = libro.autor,
                url_portada = libro.url_portada,
                precio = libro.precio // <-- Incluyendo precio para el delete
            )
            dao.delete(libroGuardado)
        }
    }

    fun eliminarLibro(libro: LibroGuardado) { /* Se queda igual */ }

    suspend fun isLibroGuardado(idLibro: Int): Boolean {
        return withContext(Dispatchers.IO) {
            dao.getLibroById(idLibro) != null
        }
    }
}