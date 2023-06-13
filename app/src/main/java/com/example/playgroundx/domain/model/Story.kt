package com.example.instagramclone.domain.model

import androidx.compose.ui.graphics.painter.Painter



data class Story(
    val id: Int,
    val text: String,
    val author: String,
    val handle: String,
    val time: String,
    val authorImageId: Int,
    val likesCount: Int,
    val commentsCount: Int,
    val retweetCount: Int,
    val source: String,
    val storyImageId: Int = 0
)