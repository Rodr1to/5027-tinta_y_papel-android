package com.rodrigovalverde.tinta_y_papel_android.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroGuardadoDao {
    // Inserta un libro. Si ya existe, lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(libro: LibroGuardado)

    @Delete
    suspend fun delete(libro: LibroGuardado)

    // Obtiene todos los libros guardados, ordenados por título
    @Query("SELECT * FROM libros_guardados ORDER BY titulo ASC")
    fun getLibrosGuardados(): Flow<List<LibroGuardado>>

    // Busca un libro por su ID para saber si ya está guardado
    @Query("SELECT * FROM libros_guardados WHERE id = :idLibro")
    suspend fun getLibroById(idLibro: Int): LibroGuardado?
}