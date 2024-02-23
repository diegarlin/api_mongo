package org.altbeacon.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val access_token: String)

data class RegisterRequest(val username: String, val password: String)
data class RegisterResponse(val message: String)
