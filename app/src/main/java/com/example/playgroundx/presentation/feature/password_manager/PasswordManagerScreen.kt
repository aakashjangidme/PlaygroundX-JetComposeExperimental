package com.example.playgroundx.presentation.feature.password_manager

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.playgroundx.data.local.entity.PasswordEntity
import com.example.playgroundx.presentation.composables.LoadingIndicator
import com.example.playgroundx.util.ext.textModifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PasswordManagerScreen(
    viewModel: PasswordViewModel = hiltViewModel(),
) {

    val passwordEntries by viewModel.passwordEntriesState.collectAsStateWithLifecycle()
    val loading by viewModel.loadingState.collectAsStateWithLifecycle()
    val error by viewModel.errorState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }


    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp),
            content = { Icon(Icons.Default.Add, contentDescription = "Add") })
    }

    ) {

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Password Manager",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )

            Divider()

            LoadingIndicator(loading)

            if (!error.isNullOrBlank()) {
                Text(
                    text = error.toString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.textModifier()
                )
            }

            PasswordManagerScreenContent(
                passwordEntries,
                viewModel::onPasswordEntrySelected,
                viewModel::onDeletePasswordEntry,
                {
                    viewModel.onAddPasswordEntry(it)
                    showDialog = false
                },
                showDialog,
                {
                    showDialog = false

                },
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreenContent(
    passwordEntries: List<PasswordEntity>,
    onPasswordEntrySelected: (PasswordEntity) -> Unit,
    onDeletePasswordEntry: (PasswordEntity) -> Unit,
    onAddPasswordEntry: (PasswordEntity) -> Unit,
    showDialog: Boolean = false,
    onDismissRequest: () -> Unit,
) {

    PasswordEntryList(passwordEntries, onPasswordEntrySelected, onDeletePasswordEntry)

    if (showDialog) {
        AlertDialog(onDismissRequest = onDismissRequest) {
            PasswordEntryForm(onAddPasswordEntry)
        }
    }
}


@Composable
fun PasswordEntryList(
    passwordEntries: List<PasswordEntity>,
    onItemClick: (PasswordEntity) -> Unit,
    onDeleteClick: (PasswordEntity) -> Unit,
) {
    println("passwordEntries.size" + passwordEntries.size)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(passwordEntries) { passwordEntry ->
            PasswordEntryItem(passwordEntry, onItemClick, onDeleteClick)
        }
    }
}

@Composable
fun PasswordEntryForm(
    onSaveClick: (PasswordEntity) -> Unit,
) {

    var website by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }


    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = website,
                onValueChange = { website = it.trim() },
                label = { Text(text = "Website") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = username,
                onValueChange = { username = it.trim() },
                label = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it.trim() },
                label = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = notes,
                onValueChange = { notes = it.trim() },
                label = { Text(text = "Notes") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {

                val newPasswordEntity = PasswordEntity(
                    website = website, username = username, password = password, notes = notes
                )

                onSaveClick(newPasswordEntity)

                website = ""
                username = ""
                password = ""
                notes = ""

            }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Save")
            }
        }
    }
}


@Composable
fun PasswordEntryItem(
    passwordEntity: PasswordEntity,
    onItemClick: (PasswordEntity) -> Unit,
    onDeleteClick: (PasswordEntity) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(passwordEntity) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = passwordEntity.website, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = passwordEntity.username, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = passwordEntity.notes, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { onDeleteClick(passwordEntity) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

