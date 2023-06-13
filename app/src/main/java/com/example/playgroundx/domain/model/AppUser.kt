package com.example.playgroundx.domain.model


data class AppUser(
    var name: String = "",
    var userName: String = "",
    var userid: String = "",
    var email: String = "",
    var password: String = "",
    var imageUrl: String = "",
    var following: List<String> = emptyList(),
    var followers: List<String> = emptyList(),
    var totalPosts: String = "",
    var bio: String = "",
    var url: String = ""
)