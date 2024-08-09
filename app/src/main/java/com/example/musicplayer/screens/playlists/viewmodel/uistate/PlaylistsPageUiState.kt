package com.example.musicplayer.screens.playlists.viewmodel.uistate

import android.net.Uri

internal data class PlaylistsPageUiState(
    public val playlistListItems: List<PlaylistListItemUiState>,
    public val isCreatePlaylistDialogOpen: Boolean,
    public val navigatingToPlaylistPage: String?,
    public val pendingNotifications: List<String>,
    public val isNavigationDrawerOpen: Boolean,
) {
    public companion object {
        public fun initial(): PlaylistsPageUiState {
            return PlaylistsPageUiState(
                playlistListItems = listOf<PlaylistListItemUiState>(),
                isCreatePlaylistDialogOpen = false,
                navigatingToPlaylistPage = null,
                pendingNotifications = listOf<String>(),
                isNavigationDrawerOpen = false,
            )
        }
    }
}

internal data class PlaylistListItemUiState(
    public val id: String,
    public val name: String,
    public val coverArt: Uri?,
    public val songCount: Int,
)