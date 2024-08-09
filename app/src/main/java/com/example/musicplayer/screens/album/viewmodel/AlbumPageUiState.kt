package com.example.musicplayer.screens.album.viewmodel

import android.net.Uri

internal data class AlbumPageUiState(
    public val id: String,
    public val name: String,
    public val artist: String,
    public val coverArt: Uri?,
    public val isPlaying: Boolean,
    public val songListItems: List<SongListItemUiState>,
    public val isPoppingPage: Boolean,
) {
    public companion object {
        public fun initial(): AlbumPageUiState {
            return AlbumPageUiState(
                id = "",
                name = "",
                artist = "",
                isPlaying = false,
                coverArt = null,
                songListItems = listOf<SongListItemUiState>(),
                isPoppingPage = false,
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