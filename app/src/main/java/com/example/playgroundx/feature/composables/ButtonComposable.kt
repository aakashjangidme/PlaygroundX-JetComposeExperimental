package com.example.playgroundx.feature.composables

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun BasicTextButton(
    @StringRes text: Int,
    modifier: Modifier,
    enabled: Boolean = true,
    action: () -> Unit,
) {
    TextButton(
        onClick = action, modifier = modifier, enabled = enabled
    ) { Text(text = stringResource(text)) }
}

@Composable
fun BasicButton(
    @StringRes text: Int,
    modifier: Modifier,
    enabled: Boolean = true,
    action: () -> Unit,
) {
    Button(
        onClick = action, modifier = modifier, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ), enabled = enabled
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}