package com.example.musicplayer

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.components.navigationdrawer.NavigationDrawerHostedState
import com.example.musicplayer.components.navigationdrawer.rememberNavigationDrawerHostedState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerOverlay
import com.example.musicplayer.components.songplayeroverlay.SongPlayerOverlayStateProvider
import com.example.musicplayer.screens.home.HomePage
import com.example.musicplayer.screens.neumorphictest.NeumorphicTrialPage
import com.example.musicplayer.screens.albums.AlbumsPage
import com.example.musicplayer.screens.PageRoutes
import com.example.musicplayer.screens.addsongstoplaylist.AddSongsToPlaylistPage
import com.example.musicplayer.screens.album.AlbumPage
import com.example.musicplayer.screens.playlist.PlaylistPage
import com.example.musicplayer.screens.playlists.PlaylistsPage
import com.example.musicplayer.screens.selectsong.SelectSongPage
import com.example.musicplayer.screens.songqueue.SongQueuePage
import com.example.musicplayer.screens.splashscreen.SplashScreenPage
import com.example.musicplayer.ui.theme.RootThemeProvider

@Composable
@Preview
fun App() {
    val navHostController: NavHostController = rememberNavController()
    val navigationDrawerState: NavigationDrawerHostedState = rememberNavigationDrawerHostedState()

    RootThemeProvider {
        SongPlayerOverlayStateProvider {
            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(
                    navController = navHostController,
                    startDestination = PageRoutes.splashScreen,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                ) {
                    composable(PageRoutes.splashScreen) { SplashScreenPage(navHostController) }

                    composable(PageRoutes.home) { HomePage() }

                    composable(PageRoutes.songs) {
                        SelectSongPage(navHostController, navigationDrawerState)
                    }

                    composable(PageRoutes.albums) {
                        AlbumsPage(navHostController = navHostController, navigationDrawerState)
                    }

                    composable(
                        PageRoutes.album,
                        arguments = listOf(navArgument("albumId") { type = NavType.StringType }),
                    ) { backStackEntry ->
                        AlbumPage(
                            id = backStackEntry.arguments!!.getString("albumId")!!,
                            navHostController = navHostController,
                        )
                    }

                    composable(PageRoutes.playlists) {
                        PlaylistsPage(
                            navigationDrawerState = navigationDrawerState,
                            navHostController = navHostController
                        )
                    }

                    composable(
                        PageRoutes.playlist,
                        arguments = listOf<NamedNavArgument>(
                            navArgument("playlistId") {
                                type = NavType.StringType
                            }
                        )
                    ) { backStackEntry ->
                        val playlistId: String = backStackEntry.arguments!!.getString("playlistId")!!

                        PlaylistPage(id = playlistId, navHostController = navHostController)
                    }

//                    composable(
//                        PageRoutes.addSongsToPlaylist,
//                        arguments = listOf(
//                            navArgument("playlistId") {
//                                navArgument("playlistId") {
//                                    type = NavType.StringType
//                                }
//                            }
//                        )
//                    ) { backstackEntry ->
//                        val playlistId = backstackEntry.arguments!!.getString("playlistId")!!
//
//                        AddSongsToPlaylistPage(playlistId, navHostController)
//                    }

                    composable(PageRoutes.songQueue) { SongQueuePage() }

                    composable("test_page") { NeumorphicTrialPage() }
                }

                SongPlayerOverlay()
            }
        }
    }
}