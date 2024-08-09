package com.example.musicplayer.screens.playlists

import com.example.musicplayer.screens.playlists.viewmodel.PlaylistsPageViewModel
import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistsPageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PreviewViewModel(uiState: PlaylistsPageUiState) : PlaylistsPageViewModel {
    override fun pageOpened() { }

    override fun createPlaylistButtonClicked() { }

    override fun createPlaylistDialogClosed(result: Boolean) { }

    override fun playlistListItemClicked(id: String) { }

    override fun notificationShown() { }

    override fun navigatedToPlaylistPage() { }

    override fun navigationDrawerButtonClicked() { }


    override val uiState: StateFlow<PlaylistsPageUiState> = MutableStateFlow(uiState)
}