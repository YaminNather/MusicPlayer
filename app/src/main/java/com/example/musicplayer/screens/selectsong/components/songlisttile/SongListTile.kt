package com.example.musicplayer.screens.selectsong.components.songlisttile

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.screens.albums.components.albumlisttile.disc.Disc
import com.example.musicplayer.screens.selectsong.viewmodel.PreviewViewModel
import com.example.musicplayer.screens.selectsong.viewmodel.SelectSongPageViewModel
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SongListItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
internal fun SongListTile(uiState: SongListItemUiState, viewModel: SelectSongPageViewModel) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    CustomListTile(
        headlineContent = {
            Text(
                uiState.name,
                color =
                    if (uiState.isPlaying) theme.resolvedColorScheme().primary
                    else Color.Unspecified,
                maxLines = 1,
            )
        },
        supportingContent = {
            Text(
                uiState.artist,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                maxLines = 1,
            )
        },
        leadingContent = { CoverArt(uiState.coverArt) },
        trailingContent = {
            Box(modifier = Modifier.wrapContentSize()) {
                IconButton(onClick = { viewModel.songListItemMoreButtonClicked(uiState.id) }) {
                    Icon(Icons.Default.MoreVert, null)
                }

                DropdownMenu(
                    expanded = uiState.moreMenuOpened,
                    onDismissRequest = { viewModel.songListItemMoreMenuDismissed(uiState.id) },
                ) {
                    DropdownMenuItem(
                        { Text("Add to Queue") },
                        onClick = { viewModel.songListItemAddToQueueButtonClicked(uiState.id) }
                    )
                }
            }
        },
        isHighlighted = uiState.isPlaying,
        onClick = { viewModel.songListItemClicked(uiState.id) }
    )
}

@Composable
private fun CoverArt(coverArt: Uri?) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    Box(
        modifier = Modifier
            .wrapContentSize()
            .neumorphic(height = theme.resolvedHeight(), shape = RoundedCornerShape(3000.dp))
            .clip(RoundedCornerShape(3000.dp))
            .padding(2.dp)
    ) {
        Disc(isSpinning = false) {
            val modifier: Modifier = Modifier.size(48.dp)

            if (coverArt != null) {
                AsyncImage(
                    model = coverArt,
                    contentDescription = null,
                    modifier = modifier,
                    fallback = painterResource(R.drawable.songcover),
                )
            }
            else {
                Icon(
                    modifier = modifier.padding(8.dp),
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(device = "id:Nexus S")
@Composable
private fun SongListTilePreview() {
    val viewModel: SelectSongPageViewModel = remember { PreviewViewModel() }

    RootThemeProvider {
        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(20) { index ->
                        if (index != 0) Spacer(Modifier.height(16.dp))

                        val uiState: SongListItemUiState = SongListItemUiState(
                            id = "id_$index",
                            name = "Song $index",
                            artist = "Random Artist",
                            coverArt = null,
                            isPlaying = index == 2,
                            moreMenuOpened = false,
                        )

                        SongListTile(
                            uiState,
                            viewModel
                        )
                    }
                }
            }
        }
    }
}