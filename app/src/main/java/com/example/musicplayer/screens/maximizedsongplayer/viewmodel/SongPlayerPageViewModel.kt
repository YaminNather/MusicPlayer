package com.example.musicplayer.screens.maximizedsongplayer.viewmodel

import kotlinx.coroutines.flow.StateFlow

import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState

internal interface SongPlayerPageViewModel {
    public fun pageOpened(): Unit

    public fun playButtonClicked(): Unit

    public fun nextSongButtonClicked(): Unit

    public fun previousSongButtonClicked(): Unit


    public fun progressSeeked(newProgress: Float): Unit

    public fun restartSongButtonClicked(): Unit

    public fun pageClosed(): Unit

    public val uiState: StateFlow<SongPlayerPageUiState>
}