package com.example.playgroundx.feature.profile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun ProfileImage(
    imageUrl: String?,
    initials: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val imageModifier = Modifier
        .size(120.dp)
        .clip(shape = CircleShape)
        .aspectRatio(1f)
        .background(Color.LightGray)

    Box(
        modifier = modifier
            .then(imageModifier)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            // Show the image if it is available
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            // Show the initials if the image is empty
            Text(
                text = initials,
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }

}
