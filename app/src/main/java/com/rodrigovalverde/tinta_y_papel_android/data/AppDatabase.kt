package com.rodrigovalverde.tinta_y_papel_android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// --- CAMBIO AQUÍ: VERSIÓN 2 ---
@Database(entities = [LibroGuardado::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun libroGuardadoDao(): LibroGuardadoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tinta_y_papel_db"
                )
                    // --- ASEGÚRATE QUE ESTA LÍNEA EXISTA ---
                    // Borra la base de datos si la versión cambia (de 1 a 2)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}