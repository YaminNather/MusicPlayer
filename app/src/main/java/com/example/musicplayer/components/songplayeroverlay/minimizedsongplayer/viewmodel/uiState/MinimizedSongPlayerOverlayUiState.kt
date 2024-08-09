package com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.uiState

public data class MinimizedSongPlayerOverlayUiState(
    public val isPlaying: Boolean,
    public val songName: String,
    public val artist: String,
    public val progress: Float,
) {
    public companion object {
        public fun initial(): MinimizedSongPlayerOverlayUiState {
            return MinimizedSongPlayerOverlayUiState(
                isPlaying = true,
                songName = "",
                artist = "",
                progress = 0f,
            )
        }
    }
}