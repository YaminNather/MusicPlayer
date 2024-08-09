package com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel

import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.uiState.MinimizedSongPlayerOverlayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PreviewViewModel : MinimizedSongPlayerOverlayViewModel {
    public override fun opened() { }

    public override fun pauseButtonClicked() { }

    public override fun toPreviousSongButtonClicked(): Unit { }

    public override fun toNextSongButtonClicked(): Unit { }

    public override fun closed(): Unit { }

    public override val uiState: StateFlow<MinimizedSongPlayerOverlayUiState>
        get() = _uiState.asStateFlow()


    private val _uiState: MutableStateFlow<MinimizedSongPlayerOverlayUiState> = MutableStateFlow(
        MinimizedSongPlayerOverlayUiState(
            isPlaying = true,
            songName = "Random Song Name",
            artist = "Random Artist Name",
            progress = 0.7f,
        )
    )
}