package com.example.musicplayer.screens.album

import com.example.musicplayer.screens.album.viewmodel.AlbumPageUiState
import com.example.musicplayer.screens.album.viewmodel.AlbumPageViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PreviewViewModel(uiState: AlbumPageUiState) : AlbumPageViewModel {
    override fun pageOpened() { }

    override fun songListItemClicked(id: String) { }

    override fun backButtonClicked() { }

    override fun pageClosed() { }

    override val uiState: StateFlow<AlbumPageUiState>
        get() = MutableStateFlow<AlbumPageUiState>(_uiState).asStateFlow()



    private val _uiState: AlbumPageUiState = uiState
}