package com.example.musicplayer.screens.addsongstoplaylist.viewmodel

import android.net.Uri

internal data class AddSongsToPlaylistPageUiState(
    val searchFieldValue: String,
    val songListItems: List<SongListItemUiState>,
    val isDismissed: Boolean,
    val result: Boolean?,
) {
    val selectAllCheckboxValue: Boolean get() = songListItems.isNotEmpty() && songListItems.find { e -> !e.isSelected } == null

    val okButtonEnabled: Boolean get() = songListItems.isNotEmpty() && songListItems.find { e -> e.isSelected } != null

    val selectedCount: Int get() = songListItems.filter { e -> e.isSelected }.size

    companion object {
        fun initial(): AddSongsToPlaylistPageUiState {
            return AddSongsToPlaylistPageUiState(
                searchFieldValue = "",
                songListItems = listOf(),
                isDismissed = false,
                result = null,
            )
        }
    }
}

internal data class SongListItemUiState(
    val id: String,
    val name: String,
    val artist: String,
    val coverArt: Uri?,
    val isSelected: Boolean,
    val isVisible: Boolean,
)