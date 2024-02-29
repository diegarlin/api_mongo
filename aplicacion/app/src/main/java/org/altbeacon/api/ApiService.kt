package org.altbeacon.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<JsonObject>

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<JsonObject>
}

data class LoginRequest(val username: String, val password: String)

data class RegisterRequest(val username: String, val password: String)
