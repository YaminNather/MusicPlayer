package com.example.musicplayer.screens.albums

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.AppNavigationDrawer
import com.example.musicplayer.components.AppNavigationDrawerRoute
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.components.navigationdrawer.NavigationDrawerHostedState
import com.example.musicplayer.components.navigationdrawer.rememberNavigationDrawerHostedState
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.albums.components.albumlisttile.AlbumListTile
import com.example.musicplayer.screens.albums.components.albumcard.AlbumCard
import com.example.musicplayer.screens.albums.viewmodel.PreviewViewModel
import com.example.musicplayer.screens.albums.viewmodel.AlbumsPageViewModel
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun AlbumsPage(
    navHostController: NavHostController,
    navigationDrawerState: NavigationDrawerHostedState,
    viewModel: AlbumsPageViewModel? = null
) {
    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    val finalViewModel: AlbumsPageViewModel = remember<AlbumsPageViewModel> {
         viewModel ?: koinContainer.getValue<AlbumsPageViewModel>()
    }

    val state by finalViewModel.state.collectAsState()

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        finalViewModel.pageOpened()
    }

    LaunchedEffect(state.navigationDrawerIsOpen) {
        if (navigationDrawerState.isOpen == state.navigationDrawerIsOpen) {
            return@LaunchedEffect
        }

        if (navigationDrawerState.isOpen) navigationDrawerState.close()
        else navigationDrawerState.open()
    }

    LaunchedEffect(state.pendingNotifications, state.isDisplayingNotification) {
        if (state.pendingNotifications.isEmpty() || state.isDisplayingNotification) {
            return@LaunchedEffect
        }

        snackbarHostState.showSnackbar(state.pendingNotifications[0])
        finalViewModel.notificationDisplayed()

        coroutineScope.launch {
            delay(5000)
            finalViewModel.notificationDismissed()
        }
    }

    LaunchedEffect(state.navigatingToAlbum) {
        if (state.navigatingToAlbum == null) return@LaunchedEffect

        navHostController.navigate("albums/${state.navigatingToAlbum}")
        finalViewModel.navigatedToAlbumPage()
    }

    AppNavigationDrawer(
        state = navigationDrawerState,
        currentPage = AppNavigationDrawerRoute.Albums,
        navHostController = navHostController,
    ) {
        CustomScaffold(
            topBar = {
                CustomTopAppBar(
                    navigationButton = {
                        IconButton(onClick = { finalViewModel.toggleNavigationDrawerButtonClicked() }) {
                            Icon(Icons.Default.Menu, null)
                        }
                    },
                    title = { Text("Albums") },
                    actions = { IconButton(onClick = {}) { Icon(Icons.Default.Search, null) } },
                )
            },
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text("Recently Played", style = MaterialTheme.typography.headlineSmall)
                    }

                    Spacer(Modifier.height(16.dp))

                    if (state.recentlyPlayed.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        ) {
                            items(state.recentlyPlayed.size) { index ->
                                val songUiState: AlbumsListItemUiState = state.recentlyPlayed[index]

                                AlbumCard(viewModel = finalViewModel, uiState = songUiState)
                            }
                        }
                    }
                    else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(128.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("No songs played recently")
                        }
                    }
                }

                item {
                    Text(
                        "All",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 16.dp)
                            .wrapContentSize(),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                items(state.albums.size) { index ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        AlbumListTile(state.albums[index], finalViewModel)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun SelectSongPagePreview() {
    val navHostController: NavHostController = rememberNavController()
    val navigationDrawerState: NavigationDrawerHostedState = rememberNavigationDrawerHostedState()

    val viewModel: AlbumsPageViewModel = remember { PreviewViewModel() }

    RootThemeProvider {
        AlbumsPage(navHostController, navigationDrawerState, viewModel)
    }
}