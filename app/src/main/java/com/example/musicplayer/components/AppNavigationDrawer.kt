package com.example.musicplayer.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.musicplayer.components.navigationdrawer.NavigationDrawer
import com.example.musicplayer.components.navigationdrawer.NavigationDrawerHostedState
import com.example.musicplayer.components.navigationdrawer.NavigationDrawerItem
import com.example.musicplayer.screens.PageRoutes

@Composable
internal fun AppNavigationDrawer(
    currentPage: AppNavigationDrawerRoute,
    state: NavigationDrawerHostedState,
    navHostController: NavHostController,
    content: @Composable () -> Unit,
) {
    NavigationDrawer(
        state = state,
        items = {
            for (route in AppNavigationDrawerRoute.entries) {
                NavigationDrawerItem(
                    isSelected = route == currentPage,
                    onClick = { navHostController.navigate(route.path) },
                ) {
                    Icon(route.icon, null)
                }
            }
        },
        content = content,
    )
}

internal enum class AppNavigationDrawerRoute(public val path: String, public val icon: ImageVector) {
    Home(PageRoutes.home, Icons.Default.Home),
    Songs(PageRoutes.songs, Icons.Default.MusicNote),
    Albums(PageRoutes.albums, Icons.Default.Album),
    Playlists(PageRoutes.playlists, Icons.AutoMirrored.Default.FeaturedPlayList),
    Equalizer(PageRoutes.equalizer, Icons.Default.Equalizer)
}
