package com.example.musicplayer.screens.selectsong.viewmodel.uistate

import android.net.Uri
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsPageUiState
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState

public data class SelectSongPageUiState(
    public val songs: List<SongListItemUiState>,
    public val recentlyPlayed: List<SongListItemUiState>,
    public val navigateToSongPage: Boolean,
    public val isDisplayingNotification: Boolean,
    public val pendingNotifications: List<String>,
    public val navigationDrawerIsOpen: Boolean,
) {
    public companion object {
        public fun initial(): SelectSongPageUiState {
            return SelectSongPageUiState(
                songs = listOf<SongListItemUiState>(),
                recentlyPlayed = listOf<SongListItemUiState>(),
                navigateToSongPage = false,
                isDisplayingNotification = false,
                pendingNotifications = listOf<String>(),
                navigationDrawerIsOpen = false,
            )
        }
    }
}

public data class SongListItemUiState(
    public val id: String,
    public val name: String,
    public val artist: String,
    public val coverArt: Uri?,
    public val isPlaying: Boolean,
    public val moreMenuOpened: Boolean,
)