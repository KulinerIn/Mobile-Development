package com.example.kulinerin.api

import com.example.kulinerin.auth.model.LoginResponse
import com.example.kulinerin.auth.model.RegisterResponse
import com.example.kulinerin.auth.model.Credentials
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register") fun register(@Body credentials: Credentials): Response<RegisterResponse>

    @POST("login") fun login(@Body credentials: Credentials): Response<LoginResponse>
}