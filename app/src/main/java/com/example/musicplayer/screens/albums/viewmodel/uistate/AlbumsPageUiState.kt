package com.example.musicplayer.screens.albums.viewmodel.uistate

import android.net.Uri

internal data class AlbumsPageUiState(
    public val albums: List<AlbumsListItemUiState>,
    public val recentlyPlayed: List<AlbumsListItemUiState>,
    public val navigatingToAlbum: String?,
    public val isDisplayingNotification: Boolean,
    public val pendingNotifications: List<String>,
    public val navigationDrawerIsOpen: Boolean,
) {
    public companion object {
        public fun initial(): AlbumsPageUiState {
            return AlbumsPageUiState(
                albums = listOf<AlbumsListItemUiState>(),
                recentlyPlayed = listOf<AlbumsListItemUiState>(),
                navigatingToAlbum= null,
                isDisplayingNotification = false,
                pendingNotifications = listOf<String>(),
                navigationDrawerIsOpen = false,
            )
        }
    }
}

internal data class AlbumsListItemUiState(
    public val id: String,
    public val name: String,
    public val artist: String,
    public val coverArt: Uri?,
    public val moreMenuOpened: Boolean,
)