package com.example.musicplayer.screens.album

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.components.playpausebutton.PlayPauseButton
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.PageRoutes
import com.example.musicplayer.screens.album.viewmodel.AlbumPageUiState
import com.example.musicplayer.screens.album.viewmodel.AlbumPageViewModel
import com.example.musicplayer.screens.album.viewmodel.AlbumPageViewModelFactory
import com.example.musicplayer.screens.album.viewmodel.SongListItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme
import kotlinx.datetime.format.Padding

@Composable
internal fun AlbumPage(
    id: String,
    navHostController: NavHostController,
    viewModel: AlbumPageViewModel? = null,
) {
    val internalViewModel: AlbumPageViewModel = remember {
        viewModel ?: koinContainer.getValue<AlbumPageViewModelFactory>().build(id)
    }

    val uiState by internalViewModel.uiState.collectAsState()

    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    DisposableEffect(Unit) {
        internalViewModel.pageOpened()

        onDispose { internalViewModel.pageClosed() }
    }

    LaunchedEffect(uiState.isPoppingPage) {
        if (uiState.isPoppingPage) {
            navHostController.popBackStack()
        }
    }

    CustomScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                navigationButton = {
                    IconButton(onClick = { internalViewModel.backButtonClicked() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Favorite, null, tint = theme.resolvedColorScheme().primary)
                        }

                        Spacer(Modifier.width(32.dp))

                        Box(modifier = Modifier.neumorphic().padding(4.dp)) {
                            AsyncImage(
                                uiState.coverArt,
                                null,
                                modifier = Modifier
                                    .size(160.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                placeholder = painterResource(R.drawable.songcover),
                            )
                        }

                        Spacer(Modifier.width(32.dp))

                        IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) }
                    }

                    Spacer(Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                uiState.name,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.headlineSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )

                            Spacer(Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painterResource(R.drawable.songcover),
                                    null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(RoundedCornerShape(3000.dp)),
                                )

                                Spacer(Modifier.width(8.dp))

                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = uiState.artist,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }

                        Spacer(Modifier.width(16.dp))

                        PlayPauseButton(isPlaying = uiState.isPlaying) {}
                    }

                    Spacer(Modifier.height(32.dp))
                }
            }

            items(uiState.songListItems.size) { index ->
                val songListItem: SongListItemUiState = uiState.songListItems[index]

                CustomListTile(
                    modifier = Modifier
                        .fillMaxWidth(),
                    headlineContent = {
                        Text(
                            songListItem.name,
                            color =
                            if (songListItem.isPlaying) theme.resolvedColorScheme().primary
                            else Color.Unspecified,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    supportingContent = {
                        Text(
                            songListItem.artist,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreVert, null)
                        }
                    },
                    isHighlighted = songListItem.isPlaying,
                    onClick = {
                        internalViewModel.songListItemClicked(songListItem.id)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlbumPagePreview() {
    val navHostController: NavHostController = rememberNavController()
    val viewModel: PreviewViewModel = remember {
        val uiState: AlbumPageUiState = AlbumPageUiState(
            id = "Album id",
            name = "Third Person Shooter - MassTamilan.com",
            artist = "Drake - MassTamilan.com",
            coverArt = null,
            isPlaying = false,
            songListItems = List(5) { index ->
                SongListItemUiState(
                    id = "id_$index",
                    name = "Song $index",
//                    name = "Song $index is a great name for a song I'm sure",
                    artist = if (index % 2 == 0) "Drake" else "J Cole",
                    isPlaying = index == 3,
                )
            },
            isPoppingPage = true,
        )

        PreviewViewModel(uiState)
    }

    RootThemeProvider {
        AlbumPage(id = "id", navHostController, viewModel = viewModel)
    }
}