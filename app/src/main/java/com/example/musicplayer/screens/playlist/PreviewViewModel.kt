package com.example.musicplayer.screens.playlist

import com.example.musicplayer.screens.playlist.viewmodel.PlaylistPageUiState
import com.example.musicplayer.screens.playlist.viewmodel.PlaylistPageViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PreviewViewModel(uiState: PlaylistPageUiState) : PlaylistPageViewModel {
    override fun pageOpened() { }

    override fun songListItemClicked(id: String) { }

    override fun addSongsButtonClicked() {}

    override fun addSongsToPlaylistPageDismissed() {}

    override fun addSongsToPlaylistPageReturnedResult(result: Boolean) { }

    override fun pendingNotificationsDisplayed() { }

    override fun backButtonClicked() { }

    override fun pageClosed() { }

    override val uiState: StateFlow<PlaylistPageUiState> = MutableStateFlow(uiState)
}