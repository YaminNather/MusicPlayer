package com.example.musicplayer.screens.addsongstoplaylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.addsongstoplaylist.components.songlistitem.SongListItem
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.AddSongsToPlaylistPageViewModel
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.AddSongsToPlaylistPageViewModelImplFactory
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.components.textfield.TextField

@Composable
internal fun AddSongsToPlaylistPage(
    playlistId: String,
    onDismissRequest: () -> Unit,
    onResult: (didAdd: Boolean) -> Unit,
    viewModel: AddSongsToPlaylistPageViewModel? = null
) {
    val internalViewModel = remember {
        return@remember viewModel
            ?: koinContainer.getValue<AddSongsToPlaylistPageViewModelImplFactory>().build(playlistId)
    }

    val uiState by internalViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        internalViewModel.pageOpened()
    }

    LaunchedEffect(uiState.isDismissed) {
        if (uiState.isDismissed) {
            onDismissRequest()
        }
    }

    LaunchedEffect(uiState.result) {
        val result: Boolean = uiState.result ?: return@LaunchedEffect
        onResult(result)
    }

    CustomScaffold(
        topBar = {
            CustomTopAppBar(
                navigationButton = {
                    IconButton(onClick = { internalViewModel.backButtonClicked() }) {
                        Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, null)
                    }
                },
                title = { Text("Add Songs") },
                actions = {
                    IconButton(onClick = { internalViewModel.okButtonClicked() }) {
                        Icon(Icons.Default.Check, null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.searchFieldValue,
                    onValueChange = { value -> internalViewModel.searchFieldValueChanged(value) },
                    placeholder = { Text("Search song") },
                    trailingIcon = { Icon(Icons.Default.Search, null) },
                )

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = uiState.selectAllCheckboxValue,
                        onCheckedChange = {
                            internalViewModel.selectAllCheckboxToggled(!uiState.selectAllCheckboxValue)
                        },
                    )

                    Text("Select All")

                    if (uiState.selectedCount != 0) {
                        Spacer(Modifier.weight(1f))

                        Text("${uiState.selectedCount} selected")
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
            ) {
                items(uiState.songListItems.size) { index ->
                    if (!uiState.songListItems[index].isVisible) return@items

                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (index != 0) {
                            Spacer(Modifier.height(16.dp))
                        }

                        SongListItem(internalViewModel, uiState.songListItems[index])
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddSongsToPlaylistPagePreview() {
    val previewViewModel = remember { PreviewViewModel(previewUiState) }

    RootThemeProvider {
        AddSongsToPlaylistPage(
            "test_id",
            onDismissRequest = {},
            onResult = { _ -> },
            viewModel = previewViewModel
        )
    }
}