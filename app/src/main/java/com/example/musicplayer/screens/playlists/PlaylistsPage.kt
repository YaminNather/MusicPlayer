package com.example.musicplayer.screens.playlists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.R
import com.example.musicplayer.components.AppNavigationDrawer
import com.example.musicplayer.components.AppNavigationDrawerRoute
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.components.navigationdrawer.NavigationDrawerHostedState
import com.example.musicplayer.components.navigationdrawer.rememberNavigationDrawerHostedState
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.playlists.compoenents.playlistlistitem.PlaylistListItem
import com.example.musicplayer.screens.playlists.components.createplaylistdialog.CreatePlaylistDialog
import com.example.musicplayer.screens.playlists.viewmodel.PlaylistsPageViewModel
import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistListItemUiState
import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistsPageUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic

@Composable
internal fun PlaylistsPage(
    navigationDrawerState: NavigationDrawerHostedState,
    navHostController: NavHostController,
    viewModel: PlaylistsPageViewModel? = null,
) {
    val internalViewModel = remember {
        viewModel ?: koinContainer.getValue<PlaylistsPageViewModel>()
    }

    val uiState: PlaylistsPageUiState by internalViewModel.uiState.collectAsState()

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        internalViewModel.pageOpened()
    }

    LaunchedEffect(uiState.pendingNotifications) {
        if (uiState.pendingNotifications.isEmpty()) return@LaunchedEffect

        snackbarHostState.showSnackbar(uiState.pendingNotifications[0])
        internalViewModel.notificationShown()
    }

    LaunchedEffect(uiState.isNavigationDrawerOpen) {
        if (uiState.isNavigationDrawerOpen) {
            navigationDrawerState.open()
        }
        else {
            navigationDrawerState.close()
        }
    }

    LaunchedEffect(uiState.navigatingToPlaylistPage) {
        if (uiState.navigatingToPlaylistPage == null) return@LaunchedEffect

        navHostController.navigate("playlists/${uiState.navigatingToPlaylistPage}")
        internalViewModel.navigatedToPlaylistPage()
    }

    AppNavigationDrawer(
        AppNavigationDrawerRoute.Playlists,
        state = navigationDrawerState,
        navHostController = navHostController,
    ) {
        CustomScaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopAppBar(internalViewModel) },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    item {
                        Column {
                            CreatePlaylistButton(internalViewModel)

                            Spacer(Modifier.height(16.dp))
                        }
                    }

                    items(uiState.playlistListItems.size) { index ->
                        val itemUiState = uiState.playlistListItems[index]

                        PlaylistListItem(internalViewModel, itemUiState)
                    }
                }

                if (uiState.isCreatePlaylistDialogOpen) {
                    CreatePlaylistDialog(
                        onDismissRequest = { result ->
                            internalViewModel.createPlaylistDialogClosed(result)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TopAppBar(viewModel: PlaylistsPageViewModel) {
    CustomTopAppBar(
        navigationButton = {
            IconButton(onClick = { viewModel.navigationDrawerButtonClicked() }) {
                Icon(Icons.Default.Menu, null)
            }
        },
        title = { Text("Playlists") },
        actions = {
            IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
        }
    )
}

@Composable
private fun CreatePlaylistButton(viewModel: PlaylistsPageViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { viewModel.createPlaylistButtonClicked() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .neumorphic()
                .padding(2.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Default.Add, null)
        }

        Spacer(Modifier.width(16.dp))

        Text("Create New Playlist")
    }
}

@Preview()
@Composable
private fun ComposablePreview() {
    val navigationDrawerState: NavigationDrawerHostedState = rememberNavigationDrawerHostedState()
    val navHostController: NavHostController = rememberNavController()

    val viewModel = remember<PreviewViewModel> {
        PreviewViewModel(
            uiState = PlaylistsPageUiState(
                playlistListItems = List(5) { index ->
                    PlaylistListItemUiState(
                        id = "playlist_$index",
                        name = "Playlist $index",
                        coverArt = null,
                        songCount = index + 1,
                    )
                },
                isCreatePlaylistDialogOpen = false,
                navigatingToPlaylistPage = null,
                pendingNotifications = listOf<String>(),
                isNavigationDrawerOpen = false,
            )
        )
    }

    RootThemeProvider {
        PlaylistsPage(navigationDrawerState, navHostController, viewModel = viewModel)
    }
}