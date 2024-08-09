package com.example.musicplayer.screens.album.viewmodel

import kotlinx.coroutines.flow.StateFlow

internal interface AlbumPageViewModel {
    public fun pageOpened(): Unit

    public fun songListItemClicked(id: String): Unit

    public fun backButtonClicked(): Unit

    public fun pageClosed(): Unit

    public val uiState: StateFlow<AlbumPageUiState>
}