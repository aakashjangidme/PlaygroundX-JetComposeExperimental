package com.example.playgroundx.feature.profile.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playgroundx.R
import com.example.playgroundx.common.ext.basicButton
import com.example.playgroundx.common.ext.fieldModifier
import com.example.playgroundx.feature.components.BasicButton
import com.example.playgroundx.feature.components.BasicField

@Composable
fun EditOverlay(
    initialValue: String,
    onValueChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    // Declare a mutable state to hold the edited value
    var editedValue by remember { mutableStateOf(initialValue) }

    // Content of the edit overlay
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BasicField(
            "Edit",
            initialValue,
            { value: String -> editedValue = value },
            Modifier.fieldModifier()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save and dismiss buttons
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicButton(
                text = R.string.save, Modifier.basicButton(), action = onSaveClick
            )
            BasicButton(
                text = R.string.dismiss, Modifier.basicButton(), action = onDismissClick
            )
        }
    }
}
