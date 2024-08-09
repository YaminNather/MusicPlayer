package com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel

import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.uiState.MinimizedSongPlayerOverlayUiState
import kotlinx.coroutines.flow.StateFlow

internal interface MinimizedSongPlayerOverlayViewModel {
    public fun opened(): Unit

    public fun pauseButtonClicked(): Unit

    public fun toPreviousSongButtonClicked(): Unit

    public fun toNextSongButtonClicked(): Unit

    public fun closed(): Unit

    public val uiState: StateFlow<MinimizedSongPlayerOverlayUiState>
}