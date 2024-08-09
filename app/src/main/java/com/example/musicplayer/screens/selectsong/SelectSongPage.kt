package com.example.musicplayer.screens.selectsong

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.components.AppNavigationDrawer
import com.example.musicplayer.components.AppNavigationDrawerRoute
import com.example.musicplayer.components.appbar.AppBar
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.components.navigationdrawer.NavigationDrawerHostedState
import com.example.musicplayer.components.navigationdrawer.rememberNavigationDrawerHostedState
import com.example.musicplayer.components.songplayeroverlay.LocalSongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerType
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.selectsong.components.songlisttile.SongListTile
import com.example.musicplayer.screens.selectsong.components.songrack.songcard.SongCard
import com.example.musicplayer.screens.selectsong.viewmodel.PreviewViewModel
import com.example.musicplayer.screens.selectsong.viewmodel.SelectSongPageViewModel
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SelectSongPageUiState
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SongListItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.theme.LocalNeumorphicTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SelectSongPage(
    navHostController: NavHostController,
    navigationDrawerState: NavigationDrawerHostedState,
    viewModel: SelectSongPageViewModel? = null
) {
    val songPlayerOverlayState: SongPlayerOverlayState = LocalSongPlayerOverlayState.current!!

    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    val finalViewModel: SelectSongPageViewModel = remember<SelectSongPageViewModel> {
         viewModel ?: koinContainer.getValue<SelectSongPageViewModel>()
    }

    val state by finalViewModel.state.collectAsState()

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    DisposableEffect(Unit) {
        finalViewModel.pageOpened()

        onDispose { finalViewModel.pageClosed() }
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

    LaunchedEffect(state.navigateToSongPage) {
        if (!state.navigateToSongPage) return@LaunchedEffect

        songPlayerOverlayState.setOpenSongPlayerType(SongPlayerType.Maximized)
        finalViewModel.navigatedToSongPlayerPage()
    }

    val backgroundColor: Color = LocalNeumorphicTheme.current!!.resolvedColorScheme().background

    AppNavigationDrawer(
        state = navigationDrawerState,
        currentPage = AppNavigationDrawerRoute.Songs,
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
                    title = { Text("Songs") },
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
                    RecentlyPlayedSection(finalViewModel, state)
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

                items(state.songs.size) { index ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        SongListTile(state.songs[index], finalViewModel)
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentlyPlayedSection(viewModel: SelectSongPageViewModel, state: SelectSongPageUiState) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 16.dp)) {
            Text("Recently Played", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))
        }

        if (state.recentlyPlayed.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(state.recentlyPlayed.size) { index ->
                    val songUiState: SongListItemUiState =
                        state.recentlyPlayed[index]

                    SongCard(viewModel = viewModel, uiState = songUiState)
                }
            }
        } else {
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

}

@Composable
@Preview
private fun SelectSongPagePreview() {
    val navHostController: NavHostController = rememberNavController()
    val navigationDrawerState: NavigationDrawerHostedState = rememberNavigationDrawerHostedState()

    val viewModel: SelectSongPageViewModel = remember { PreviewViewModel() }

    RootThemeProvider {
        val songPlayerOverlayState: SongPlayerOverlayState = SongPlayerOverlayState(
            songPlayerType = null,
            setOpenSongPlayerType = { _ -> },
        )
        CompositionLocalProvider(LocalSongPlayerOverlayState.provides(songPlayerOverlayState)) {
            SelectSongPage(
                viewModel = viewModel,
                navHostController = navHostController,
                navigationDrawerState = navigationDrawerState,
            )
        }
    }
}