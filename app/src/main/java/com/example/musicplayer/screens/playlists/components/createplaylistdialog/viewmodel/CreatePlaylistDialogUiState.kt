package com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel

internal data class CreatePlaylistDialogUiState(
    public val nameFieldValue: String,
    public val result: Boolean?,
) {
    public companion object {
        public fun initial(): CreatePlaylistDialogUiState {
            return CreatePlaylistDialogUiState(
                nameFieldValue = "",
                result = null,
            )
        }
    }
}