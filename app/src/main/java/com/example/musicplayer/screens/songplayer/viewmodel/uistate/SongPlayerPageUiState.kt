package com.example.musicplayer.screens.songplayer.viewmodel.uistate

import android.net.Uri
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal data class SongPlayerPageUiState(
    public val songName: String,
    public val artistName: String,
    public val coverArt: Uri?,
    public val isPaused: Boolean,
    public val songDuration: Duration,
    public val songProgress: Duration,
    public val songProgressFactor: Float,
) {
    public companion object {
        public fun initial(): SongPlayerPageUiState {
            return SongPlayerPageUiState(
                songName = "",
                artistName = "",
                isPaused = false,
                coverArt = null,
                songDuration = 0.seconds,
                songProgress = 0.seconds,
                songProgressFactor = 0f,
            )
        }
    }
}