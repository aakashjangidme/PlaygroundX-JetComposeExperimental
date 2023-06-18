package com.example.playgroundx.presentation.feature.settings


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp)
            .fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon, contentDescription = title, modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = title, modifier = Modifier.fillMaxWidth())
    }
}

/**
 * Overloaded composable to accept Painter as icon
 */
@Composable
fun SettingItem(icon: Painter, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp)
            .fillMaxWidth(), Arrangement.Start, Alignment.CenterVertically
    ) {
        Icon(
            painter = icon, contentDescription = null, modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = title, modifier = Modifier.fillMaxWidth())
    }
}


@Composable
fun SettingTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
    )
}