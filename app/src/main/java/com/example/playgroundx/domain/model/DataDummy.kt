package com.example.playgroundx.domain.model

import com.example.instagramclone.domain.model.Story
import com.example.playgroundx.R


object DataDummy {
    val story = Story(
        1,
        "Jetpack compose is the next thing for andorid. Declarative UI is the way to go for all screens.",
        "The Verge",
        "@verge",
        "12m",
        R.drawable.ic_offline,
        100,
        12,
        15,
        "Twitter for web"
    )

    val storyList = listOf(
        story,
        story.copy(
            id = 3,
            author = "Amazon",
            handle = "@Amazon",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 4,
            author = "Facebook",
            handle = "@Facebook",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 5,
            author = "Instagram",
            handle = "@Instagram",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 6,
            author = "Twitter",
            handle = "@Twitter",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 7,
            author = "Netflix",
            handle = "@Netflix",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 8,
            author = "Tesla",
            handle = "@Tesla",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 9,
            author = "Microsoft",
            handle = "@Microsoft",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 3,
            author = "Tencent",
            handle = "@Tencent",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 4,
            author = "Snapchat",
            handle = "@Snapchat",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 5,
            author = "Snapchat",
            handle = "@Snapchat",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 6,
            author = "Tiktok",
            handle = "@Tiktok",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 7,
            author = "Samsung",
            handle = "@Samsung",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 8,
            author = "Youtube",
            handle = "@Youtube",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 9,
            author = "Gmail",
            handle = "@Gmail",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 3,
            author = "Android",
            handle = "@Android",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 4,
            author = "Whatsapp",
            handle = "@Whatsapp",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 5,
            author = "Telegram",
            handle = "@Telegram",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 6,
            author = "Spotify",
            handle = "@Spotify",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 7,
            author = "WeChat",
            handle = "@WeChat",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 8,
            author = "Airbnb",
            handle = "@Airbnb",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 9,
            author = "LinkedIn",
            handle = "@LinkedIn",
            authorImageId = R.drawable.ic_offline,
            time = "1h"
        ),
        story.copy(
            id = 6,
            author = "Shazam",
            handle = "@Shazam",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 7,
            author = "Chrome",
            handle = "@Chrome",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 6,
            author = "Safari",
            handle = "@Safari",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        ),
        story.copy(
            id = 7,
            author = "Disney",
            handle = "@Disney",
            authorImageId = R.drawable.ic_offline,
            storyImageId = R.drawable.ic_offline,
            time = "11m"
        )

    )

}