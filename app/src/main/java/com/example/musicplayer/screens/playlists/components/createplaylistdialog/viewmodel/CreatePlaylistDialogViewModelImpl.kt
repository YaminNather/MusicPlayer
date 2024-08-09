package com.example.musicplayer.screens.playlists.components.createplaylistdialog.viewmodel

import com.example.businesslogic.playlistapi.PlaylistApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CreatePlaylistDialogViewModelImpl(
    private val playlistApi: PlaylistApi,
) : CreatePlaylistDialogViewModel {
    override fun nameFieldChanged(value: String) {
        _uiState.update { uiStateValue -> uiStateValue.copy(nameFieldValue = value) }
    }

    override fun addButtonClicked() {
        coroutineScope.launch {
            playlistApi.createPlaylist(_uiState.value.nameFieldValue)
            _uiState.update { value -> value.copy(result = true) }
        }
    }

    override fun cancelButtonClicked() {
        _uiState.update { value -> value.copy(result = false) }
    }

    override fun dismissed() {
        _uiState.update { value -> value.copy(result = false)}
    }

    override val uiState: StateFlow<CreatePlaylistDialogUiState> get() = _uiState.asStateFlow()



    private val _uiState: MutableStateFlow<CreatePlaylistDialogUiState>
        = MutableStateFlow<CreatePlaylistDialogUiState>(CreatePlaylistDialogUiState.initial())

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
}