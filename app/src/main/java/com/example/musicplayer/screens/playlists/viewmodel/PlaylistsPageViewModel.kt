package com.example.musicplayer.screens.playlists.viewmodel

import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistsPageUiState
import kotlinx.coroutines.flow.StateFlow

internal interface PlaylistsPageViewModel {
    public fun pageOpened(): Unit

    public fun createPlaylistButtonClicked(): Unit

    public fun createPlaylistDialogClosed(result: Boolean): Unit

    public fun playlistListItemClicked(id: String): Unit

    public fun notificationShown(): Unit

    public fun navigatedToPlaylistPage(): Unit

    public fun navigationDrawerButtonClicked(): Unit


    public val uiState: StateFlow<PlaylistsPageUiState>
}