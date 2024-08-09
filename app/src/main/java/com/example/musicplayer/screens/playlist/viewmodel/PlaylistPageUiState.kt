package com.example.musicplayer.screens.playlist.viewmodel

import android.net.Uri

internal data class PlaylistPageUiState(
    public val name: String,
    public val coverArt: Uri?,
    public val songs: List<SongListItemUiState>,
    public val pendingNotifications: List<String>,
    public val isAddSongToPlaylistPageOpen: Boolean,
    public val isNavigatingBack: Boolean,
) {
    public companion object {
        public fun initial(): PlaylistPageUiState {
            return PlaylistPageUiState(
                name = "",
                coverArt = null,
                songs = listOf<SongListItemUiState>(),
                pendingNotifications = listOf(),
                isAddSongToPlaylistPageOpen = false,
                isNavigatingBack = false,
            )
        }
    }
}

internal data class SongListItemUiState(
    public val id: String,
    public val name: String,
    public val artist: String,
    public val isPlaying: Boolean,
)