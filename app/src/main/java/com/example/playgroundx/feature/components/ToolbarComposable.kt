@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.playgroundx.feature.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun BasicToolbar(@StringRes title: Int, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(stringResource(title)) }, modifier = modifier
            .padding(0.dp)
            .then(modifier)
    )
}

@Composable
fun ActionToolbar(
    @StringRes title: Int,
    @DrawableRes endActionIcon: Int,
    modifier: Modifier,
    endAction: () -> Unit,
) {
    TopAppBar(title = { Text(stringResource(title)) }, actions = {
        Box(modifier) {
            IconButton(onClick = endAction) {
                Icon(painter = painterResource(endActionIcon), contentDescription = "Action")
            }
        }
    })
}

@Composable
private fun toolbarColor(darkTheme: Boolean = isSystemInDarkTheme()): TopAppBarColors? {
    return null;
}
