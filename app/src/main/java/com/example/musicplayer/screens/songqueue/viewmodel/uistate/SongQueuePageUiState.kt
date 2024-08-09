package com.example.musicplayer.screens.songqueue.viewmodel.uistate

public data class SongQueuePageUiState(
    public val songQueueItems: List<SongQueueItemUiState>,
) {
    public companion object {
        public fun initial(): SongQueuePageUiState {
            return SongQueuePageUiState(songQueueItems = listOf<SongQueueItemUiState>())
        }
    }
}

public data class SongQueueItemUiState(
    public val id: Int,
    public val name: String,
    public val artist: String,
    public val isPlaying: Boolean,
    public val isMoreOptionsMenuOpen: Boolean,
)