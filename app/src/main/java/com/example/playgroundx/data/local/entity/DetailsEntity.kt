package com.example.playgroundx.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class DetailsEntity constructor(
    @PrimaryKey val user: String,
    val avatar: String,
    val name: String,
    val userSince: String,
    val location: String
)

