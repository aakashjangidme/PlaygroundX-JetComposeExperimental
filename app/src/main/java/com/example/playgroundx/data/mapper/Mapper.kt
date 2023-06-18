package com.example.playgroundx.data.mapper

import com.example.playgroundx.data.local.entity.DetailsEntity
import com.example.playgroundx.data.local.entity.UserEntity
import com.example.playgroundx.data.remote.dto.DetailsDto
import com.example.playgroundx.data.remote.dto.UserDto


// Network to Local UserDto
fun UserDto.toLocal() = UserEntity(
    id = id, avatar = avatarUrl, username = login
)


@JvmName("networkToLocal")
fun List<UserDto>.toLocal() = map(UserDto::toLocal)



// Network to Local DetailsDto
fun DetailsDto.toLocal() = DetailsEntity(
    user = login,
    avatar = avatarUrl,
    name = name ?: "",
    userSince = createdAt ?: "",
    location = location ?: ""
)


