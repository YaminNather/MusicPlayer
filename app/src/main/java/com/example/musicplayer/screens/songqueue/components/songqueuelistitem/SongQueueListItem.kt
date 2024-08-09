package com.example.musicplayer.screens.songqueue.components.songqueuelistitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.screens.songqueue.viewmodel.PreviewViewModel
import com.example.musicplayer.screens.songqueue.viewmodel.SongQueuePageViewModel
import com.example.musicplayer.screens.songqueue.viewmodel.uistate.SongQueueItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton

@Composable
internal fun SongQueueListItem(uiState: SongQueueItemUiState, viewModel: SongQueuePageViewModel) {
    CustomListTile(
        modifier = Modifier.clickable {
            viewModel.queueItemClicked(uiState.id)
        },
//        leadingContent = { Icon(Icons.Default.Reorder, null) },
        headlineContent = { Text(uiState.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        supportingContent = { Text(uiState.artist, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        trailingContent = {
            IconButton(onClick = { viewModel.queueItemMoreOptionsButtonClicked(uiState.id) }) {
                Icon(Icons.Default.MoreVert, null)
            }

            DropdownMenu(
                expanded = uiState.isMoreOptionsMenuOpen,
                onDismissRequest = { viewModel.queueItemMoreOptionsMenuDismissed(uiState.id) }
            ) {
                DropdownMenuItem(
                    text = { Text("Remove") },
                    onClick = { viewModel.removeQueueItemButtonClicked(uiState.id) }
                )
            }
        },
    )
}

@Preview
@Composable
private fun SongListItemPreview() {
    val viewModel: PreviewViewModel = remember { PreviewViewModel() }

    RootThemeProvider {
        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(vertical = 16.dp)
            ) {
                for (i in 0..5) {
                    val uiState: SongQueueItemUiState = SongQueueItemUiState(
                        id = 0,
                        name = "Song $i",
                        artist = "Random Artist",
                        isPlaying = i == 0,
                        isMoreOptionsMenuOpen = false,
                    )
                    SongQueueListItem(uiState, viewModel)
                }
            }
        }
    }
}