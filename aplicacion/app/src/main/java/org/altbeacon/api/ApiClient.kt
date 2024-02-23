package org.altbeacon.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import android.content.Context
//suspend lo que hace es bloquear el hilo donde se ejecuta para hacer una operación asíncrona sin parar los demás procesos para así
//la aplicación sigua ejecutándose mientras llama a la API
object ApiClient {
    private const val BASE_URL = "http://192.168.1.184:5000" // Reemplaza con la URL de tu API

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun login(context: Context, username: String, password: String): String? {
        val response = apiService.login(LoginRequest(username, password))
        return if (response.isSuccessful) {
            response.body()?.access_token
        } else {
            Toast.makeText(context, "Inicio de sesión fallido. Por favor, verifica tus credenciales.", Toast.LENGTH_SHORT).show()
            null
        }
    }

}

