package com.example.playgroundx.domain.model


data class AppUser(
    var userid: String = "",
    var name: String = "",
    var userName: String = "",
    var email: String = "",
    var password: String = "",
    var imageUrl: String = "",
    var phoneNumber: String = "",
    var following: List<String> = emptyList(),
    var followers: List<String> = emptyList(),
    var totalPosts: String = "",
    var bio: String = "",
    var url: String = "",
)