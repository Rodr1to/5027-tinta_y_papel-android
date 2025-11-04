package com.rodrigovalverde.tinta_y_papel_android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LibroGuardado::class], version = 1)
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
                    .fallbackToDestructiveMigration() // Útil para desarrollo
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}