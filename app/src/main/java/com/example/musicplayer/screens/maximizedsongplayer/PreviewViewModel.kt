package com.example.musicplayer.screens.maximizedsongplayer

import com.example.musicplayer.screens.songplayer.viewmodel.SongPlayerPageViewModel
import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration.Companion.minutes

internal class PreviewViewModel: SongPlayerPageViewModel {
    public override fun pageOpened(): Unit { }

    public override fun playButtonClicked(): Unit { }

    override fun nextSongButtonClicked() { }

    override fun previousSongButtonClicked() { }

    override fun progressSeeked(newProgress: Float) { }

    public override fun restartSongButtonClicked(): Unit {}

    public override fun pageClosed(): Unit { }

    public override val uiState: StateFlow<SongPlayerPageUiState> get() = _uiState

    private val _uiState: StateFlow<SongPlayerPageUiState> = MutableStateFlow<SongPlayerPageUiState>(
        SongPlayerPageUiState(
//            songName = "DANCE MONKEY",
//            artistName = "",
            songName = "Dance Monkey",
            artistName = "Tones and you",
            isPaused = false,
            coverArt = null,
            songDuration = 5.3.minutes,
            songProgress = 5.3.minutes * 0.3,
            songProgressFactor = 0.3f,
        )
    )
}