package com.example.musicplayer.screens.playlist

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.example.musicplayer.R
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.addsongstoplaylist.AddSongsToPlaylistPage
import com.example.musicplayer.screens.playlist.viewmodel.PlaylistPageUiState
import com.example.musicplayer.screens.playlist.viewmodel.PlaylistPageViewModel
import com.example.musicplayer.screens.playlist.viewmodel.PlaylistPageViewModelImplFactory
import com.example.musicplayer.screens.playlist.viewmodel.SongListItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
internal fun PlaylistPage(
    id: String,
    navHostController: NavHostController,
    viewModel: PlaylistPageViewModel? = null,
) {
    val internalViewModel: PlaylistPageViewModel = remember {
        viewModel ?: koinContainer.getValue<PlaylistPageViewModelImplFactory>().build(id)
    }
    
    val uiState by internalViewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    DisposableEffect(Unit) {
        internalViewModel.pageOpened()

        onDispose { internalViewModel.pageClosed() }
    }

    LaunchedEffect(uiState.isNavigatingBack) {
        if (uiState.isNavigatingBack) {
            navHostController.popBackStack()
        }
    }

    LaunchedEffect(uiState.pendingNotifications) {
        if (uiState.pendingNotifications.isEmpty()) return@LaunchedEffect

        for (pendingNotification in uiState.pendingNotifications) {
            snackbarHostState.showSnackbar(pendingNotification)
        }
        internalViewModel.pendingNotificationsDisplayed()
    }

    CustomScaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CustomTopAppBar(
                navigationButton = {
                    IconButton(onClick = { internalViewModel.backButtonClicked() }) {
                        Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, null)
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
        ) {
            item {
                Column {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = { internalViewModel.addSongsButtonClicked() }) {
                            Icon(Icons.Default.Add, null)
                        }

                        Spacer(Modifier.width(32.dp))

                        CoverArt()

                        Spacer(Modifier.width(32.dp))

                        IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) }
                    }

                    Spacer(Modifier.height(32.dp))

                    Text(
                        uiState.name,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${uiState.songs.size} songs",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(Modifier.height(32.dp))
                }
            }

            items(uiState.songs.size) { index ->
                SongListItem(internalViewModel, uiState.songs[index])
            }
        }
    }

    if (uiState.isAddSongToPlaylistPageOpen) {
        AddSongsToPlaylistPage(
            playlistId = id,
            onDismissRequest = { internalViewModel.addSongsToPlaylistPageDismissed() },
            onResult = { result -> internalViewModel.addSongsToPlaylistPageReturnedResult(result) }
        )
    }
}

@Composable
private fun CoverArt() {
    Box(
        modifier = Modifier
            .neumorphic()
            .padding(4.dp)
    ) {
        Image(
            painterResource(R.drawable.songcover),
            null,
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}

@Composable
private fun SongListItem(viewModel: PlaylistPageViewModel, uiState: SongListItemUiState) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    CustomListTile(
        modifier = Modifier
            .fillMaxWidth(),
        headlineContent = {
            Text(
                uiState.name,
                color =
                if (uiState.isPlaying) theme.resolvedColorScheme().primary
                else Color.Unspecified,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        supportingContent = {
            Text(
                uiState.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        trailingContent = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, null)
            }
        },
        isHighlighted = uiState.isPlaying,
        onClick = {}
    )
}

@Preview
@Composable
private fun PlaylistPagePreview() {
    val viewModel: PlaylistPageViewModel = remember {
        PreviewViewModel(
            PlaylistPageUiState(
                name = "Playlist",
                coverArt = null,
                songs = List(10) { index ->
                    SongListItemUiState(
                        id = "id_$index",
                        name = "Song $index",
                        artist = "Artist",
                        isPlaying = index == 2,
                    )
                },
                pendingNotifications = listOf(),
                isAddSongToPlaylistPageOpen = false,
                isNavigatingBack = false,
            )
        )
    }
    val navHostController: NavHostController = rememberNavController()

    RootThemeProvider {
        PlaylistPage(id = "", navHostController = navHostController, viewModel = viewModel)
    }
}