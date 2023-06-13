package com.example.playgroundx.data.remote

import com.example.playgroundx.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UsersApi {

    @GET("/repos/square/retrofit/stargazers")
    suspend fun getUsers(): List<UserDto>
}