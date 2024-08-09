package com.example.musicplayer.screens.playlists.components.createplaylistdialog

import com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel.CreatePlaylistDialogUiState
import com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel.CreatePlaylistDialogViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PreviewViewModel(uiState: CreatePlaylistDialogUiState) : CreatePlaylistDialogViewModel {
    override fun nameFieldChanged(value: String) { }

    override fun addButtonClicked() { }

    override fun cancelButtonClicked() { }

    override fun dismissed() { }

    override val uiState: StateFlow<CreatePlaylistDialogUiState> get() = _uiState



    private val _uiState: StateFlow<CreatePlaylistDialogUiState> = MutableStateFlow(uiState)
}