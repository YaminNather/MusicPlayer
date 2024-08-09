package com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel

import kotlinx.coroutines.flow.StateFlow

internal interface CreatePlaylistDialogViewModel {
    public fun nameFieldChanged(value: String): Unit

    public fun addButtonClicked(): Unit

    public fun cancelButtonClicked(): Unit

    public fun dismissed(): Unit

    public val uiState: StateFlow<CreatePlaylistDialogUiState>
}