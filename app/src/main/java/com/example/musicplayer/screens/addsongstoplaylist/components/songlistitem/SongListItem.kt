package com.example.musicplayer.screens.addsongstoplaylist.components.songlistitem

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.screens.addsongstoplaylist.PreviewViewModel
import com.example.musicplayer.screens.addsongstoplaylist.previewUiState
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.AddSongsToPlaylistPageUiState
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.AddSongsToPlaylistPageViewModel
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.SongListItemUiState
import com.example.musicplayer.screens.albums.components.albumlisttile.disc.Disc
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
internal fun SongListItem(
    viewModel: AddSongsToPlaylistPageViewModel,
    uiState: SongListItemUiState,
) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    CustomListTile(
        headlineContent = {
            Text(uiState.name, maxLines = 1)
        },
        supportingContent = {
            Text(
                uiState.artist,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                maxLines = 1,
            )
        },
        leadingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CoverArt(uiState.coverArt)
            }
        },
        isHighlighted = uiState.isSelected,
        onClick = { viewModel.songListItemClicked(uiState.id) },
    )
}

@Composable
private fun CoverArt(coverArt: Uri?) {
    val theme = LocalNeumorphicTheme.current!!

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

@Preview
@Composable
private fun SongListItemPreview() {
    val viewModel = remember { PreviewViewModel(previewUiState) }

    val uiState by viewModel.uiState.collectAsState()

    RootThemeProvider {
        CustomScaffold { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(10) { index ->
                    if (index != 0) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(Modifier.height(16.dp))

                            SongListItem(viewModel, uiState.songListItems[index])
                        }
                    }
                }
            }
        }
    }
}