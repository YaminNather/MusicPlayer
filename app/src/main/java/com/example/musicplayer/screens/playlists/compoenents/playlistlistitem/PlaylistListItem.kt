package com.example.musicplayer.screens.playlists.compoenents.playlistlistitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.screens.playlists.PreviewViewModel
import com.example.musicplayer.screens.playlists.viewmodel.PlaylistsPageViewModel
import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistListItemUiState
import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistsPageUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic

@Composable
internal fun PlaylistListItem(viewModel: PlaylistsPageViewModel, uiState: PlaylistListItemUiState) {
    CustomListTile(
        headlineContent = { Text(uiState.name) },
        supportingContent = { Text("${uiState.songCount} songs") },
        leadingContent = {
            Box(
                modifier = Modifier
                    .neumorphic()
                    .padding(2.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    painter = painterResource(id = R.drawable.songcover),
                    contentDescription = null,
                )
            }
        },
        trailingContent = {
            IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) }
        },
        onClick = {
            viewModel.playlistListItemClicked(uiState.id)
        },
    )
}

@Preview
@Composable
private fun PlaylistListItemPreview() {
    val viewModel = remember {
        PreviewViewModel(
            PlaylistsPageUiState.initial().copy(
                playlistListItems = List(5) { index ->
                    PlaylistListItemUiState(
                        id = "playlist_$index",
                        name = "Playlist $index",
                        coverArt = null,
                        songCount = index + 1,
                    )
                },
            )
        )
    }

    RootThemeProvider {
       CustomScaffold { padding ->
           LazyColumn(
               modifier = Modifier.fillMaxSize().padding(padding),
               contentPadding = PaddingValues(16.dp)
           ) {
               val playlistListItems = viewModel.uiState.value.playlistListItems

               items(playlistListItems.size) { index ->
                   PlaylistListItem(viewModel = viewModel, uiState = playlistListItems[index])
               }
           }
       }
    }
}
