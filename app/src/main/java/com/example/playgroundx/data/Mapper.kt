package com.example.playgroundx.data

import com.example.playgroundx.data.local.entity.DetailsEntity
import com.example.playgroundx.data.local.entity.UserEntity
import com.example.playgroundx.data.remote.dto.DetailsDto
import com.example.playgroundx.data.remote.dto.UserDto
import com.example.playgroundx.domain.model.Details
import com.example.playgroundx.domain.model.User


// Network to Local UserDto
fun UserDto.toLocal() = UserEntity(
    id = id, avatar = avatarUrl, username = login
)

fun UserDto.toNetwork() = User(
    id = id, avatar = avatarUrl, username = login
)

@JvmName("networkToLocal")
fun List<UserDto>.toLocal() = map(UserDto::toLocal)

@JvmName("networkToNetwork")
fun List<UserDto>.toNetwork() = map(UserDto::toNetwork)


// Local to Network/toDomain (domain model which will consumed by UI) UserDto
fun UserEntity.toNetwork() = User(
    id = id, avatar = avatar, username = username
)

@JvmName("localToNetwork")
fun List<UserEntity>.toNetwork() = map(UserEntity::toNetwork)


// Network to Local DetailsDto
fun DetailsDto.toLocal() = DetailsEntity(
    user = login,
    avatar = avatarUrl,
    name = name ?: "",
    userSince = createdAt ?: "",
    location = location ?: ""
)

// Network to Local DetailsDto
fun DetailsEntity.toNetwork() = Details(
    user = user, avatar = avatar, name = name, userSince = userSince, location = location
)
