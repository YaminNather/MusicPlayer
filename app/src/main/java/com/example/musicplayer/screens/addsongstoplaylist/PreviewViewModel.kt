package com.example.musicplayer.screens.addsongstoplaylist

import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.AddSongsToPlaylistPageUiState
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.AddSongsToPlaylistPageViewModel
import com.example.musicplayer.screens.addsongstoplaylist.viewmodel.SongListItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PreviewViewModel(uiState: AddSongsToPlaylistPageUiState) : AddSongsToPlaylistPageViewModel {
    override fun pageOpened() {}

    override fun songListItemClicked(id: String) {}

    override fun searchFieldValueChanged(newValue: String) {}

    override fun selectAllCheckboxToggled(isSelected: Boolean) {}

    override fun okButtonClicked() {}

    override fun backButtonClicked() {}

    override val uiState: StateFlow<AddSongsToPlaylistPageUiState> =  MutableStateFlow(uiState)
}

internal val previewUiState = AddSongsToPlaylistPageUiState(
    searchFieldValue = "",
    songListItems = List(10) { index ->
        SongListItemUiState(
            id = "song_$index",
            name = "Song $index",
            artist = "Artist",
            coverArt = null,
            isSelected = true,
            isVisible = true,
        )
    },
    isDismissed = false,
    result = null,
)