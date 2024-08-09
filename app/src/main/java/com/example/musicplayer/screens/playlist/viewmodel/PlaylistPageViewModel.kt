package com.example.musicplayer.screens.playlist.viewmodel

import kotlinx.coroutines.flow.StateFlow

internal interface PlaylistPageViewModel {
    public fun pageOpened(): Unit

    public fun songListItemClicked(id: String): Unit

    public fun addSongsButtonClicked(): Unit

    public fun addSongsToPlaylistPageDismissed(): Unit

    public fun addSongsToPlaylistPageReturnedResult(result: Boolean): Unit

    public fun pendingNotificationsDisplayed(): Unit

    public fun backButtonClicked(): Unit

    public fun pageClosed(): Unit

    public val uiState: StateFlow<PlaylistPageUiState>
}