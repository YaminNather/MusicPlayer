package com.example.musicplayer.screens.addsongstoplaylist.viewmodel

import kotlinx.coroutines.flow.StateFlow

internal interface AddSongsToPlaylistPageViewModel {
    fun pageOpened()

    fun songListItemClicked(id: String)

    fun searchFieldValueChanged(newValue: String)

    fun selectAllCheckboxToggled(isSelected: Boolean)

    fun okButtonClicked()

    fun backButtonClicked()

    val uiState: StateFlow<AddSongsToPlaylistPageUiState>
}