package com.example.musicplayer.screens.maximizedsongplayer.viewmodel.uistate

import android.net.Uri
import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState
import kotlin.time.Duration.Companion.milliseconds

internal data class SongPlayerPageUiState(
    public val songName: String,
    public val artistName: String,
    public val coverArt: Uri?,
    public val isPaused: Boolean,
    public val songProgress: Float,
) {
    public companion object {
        public fun initial(): SongPlayerPageUiState {
            return SongPlayerPageUiState(
                songName = "",
                artistName = "",
                isPaused = false,
                coverArt = null,
                songDuration = 0.milliseconds,
                songProgress = 0.milliseconds,
                songProgressFactor = 0f,
            )
        }
    }
}