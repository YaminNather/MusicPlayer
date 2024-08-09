package com.example.musicplayer.screens.playlists.components.createplaylistdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel.CreatePlaylistDialogUiState
import com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel.CreatePlaylistDialogViewModel
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.button.Button
import com.example.neumorphism.components.textfield.TextField
import com.example.neumorphism.core.modifiers.neumorphic

@Composable
internal fun CreatePlaylistDialog(
    onDismissRequest: (result: Boolean) -> Unit,
    viewModel: CreatePlaylistDialogViewModel? = null,
) {
    val internalViewModel: CreatePlaylistDialogViewModel = remember {
        viewModel ?: koinContainer.getValue<CreatePlaylistDialogViewModel>()
    }

    val uiState by internalViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.result) {
        if (uiState.result == null) return@LaunchedEffect

        onDismissRequest(uiState.result!!)
    }

    Dialog(onDismissRequest = { internalViewModel.dismissed() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .neumorphic()
                .padding(16.dp),
        ) {
            Text("Create Playlist", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(32.dp))

            Text("Name")

            Spacer(Modifier.height(4.dp))

            TextField(
                value = uiState.nameFieldValue,
                onValueChange = { value -> internalViewModel.nameFieldChanged(value) },
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Button(onClick = { internalViewModel.cancelButtonClicked() }) {
                    Text("Cancel", color = Color.Red)
                }

                Button(onClick = { internalViewModel.addButtonClicked() }) {
                    Text("Add")
                }
            }
        }
    }
}

@Preview
@Composable
private fun CreateNewPlaylistModalPreview() {
    val viewModel = remember { PreviewViewModel(CreatePlaylistDialogUiState.initial()) }

    RootThemeProvider {
        Box(Modifier.fillMaxSize()) {
            CustomScaffold(
                topBar = {
                    CustomTopAppBar(
                        title = { Text("Playlists") }
                    )
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) { }
            }

            CreatePlaylistDialog(onDismissRequest = {}, viewModel = viewModel)
        }
    }
}