package com.rodrigovalverde.tinta_y_papel_android.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("libros.php")
    suspend fun getLibros(): List<Libro>

    @GET("categorias.php")
    suspend fun getCategorias(): List<Categoria>

    @GET("libros_categoria.php")
    suspend fun getLibrosPorCategoria(@Query("id_categoria") idCategoria: Int): List<Libro>

    @GET("libro_detalle.php")
    suspend fun getLibroDetalle(@Query("id") idLibro: Int): Libro

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    companion object {
        private var apiService: ApiService? = null
        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://rovalverde.alwaysdata.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}