package com.example.musicplayer.screens.albums.components.albumcard

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.screens.albums.viewmodel.AlbumsPageViewModel
import com.example.musicplayer.screens.albums.viewmodel.PreviewViewModel
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.core.modifiers.neumorphic

@Composable
internal fun AlbumCard(
    modifier: Modifier = Modifier,
    viewModel: AlbumsPageViewModel,
    uiState: AlbumsListItemUiState,
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .clickable { viewModel.albumListItemClicked(uiState.id) }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .neumorphic(shape = RoundedCornerShape(3000.dp))
                .padding(4.dp),
        ) {
            if (uiState.coverArt != null) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(3000.dp)),
                    model = uiState.coverArt,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.songcover),
                )
            }
            else {
                Icon(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            uiState.name,
            modifier = Modifier.width(128.dp).wrapContentHeight(),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(4.dp))

        Text(
            uiState.artist,
            modifier = Modifier.width(128.dp).wrapContentHeight(),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(device = "id:Nexus 5")
@Composable
private fun DefaultCardPreview() {
    val viewModel: PreviewViewModel = remember { PreviewViewModel() }

    RootThemeProvider {
        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    items(5) { index ->
                        AlbumCard(
                            modifier = Modifier.fillMaxHeight(),
                            viewModel = viewModel,
                            uiState = AlbumsListItemUiState(
                                id = "id_0",
                                name = "Bad Liar",
                                artist = "Anna Hamilton",
                                coverArt =
                                    if (index != 1) Uri.parse("content://fiefjeijf")
                                    else null,
                                moreMenuOpened = false,
                            )
                        )
                    }
                }
            }
        }
    }
}