package com.example.playgroundx.data.remote.api

import com.example.playgroundx.data.remote.dto.DetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {

    @GET("/users/{user}")
    suspend fun getDetails(@Path("user") user: String): DetailsDto
}