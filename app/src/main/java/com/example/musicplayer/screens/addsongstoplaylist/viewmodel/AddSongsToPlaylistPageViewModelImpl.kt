package com.example.musicplayer.screens.addsongstoplaylist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.businesslogic.songqueryapi.SongQueryApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AddSongsToPlaylistPageViewModelImpl(
    private val playlistId: String,
    private val songQueryApi: SongQueryApi,
    private val playlistApi: PlaylistApi,
) : AddSongsToPlaylistPageViewModel, ViewModel() {
    override fun pageOpened() {
        viewModelScope.launch {
            val songsInPlaylist = playlistApi.getWithId(playlistId)!!.songs
            val songs = songQueryApi.getAll() - songsInPlaylist.toSet()

            _uiState.update { value ->
                value.copy(
                    songListItems = songs.map { song ->
                        SongListItemUiState(
                            id = song.id,
                            name = song.name,
                            artist = song.artist,
                            coverArt = song.coverArt,
                            isSelected = false,
                            isVisible = true,
                        )
                    }
                )
            }
        }
    }

    override fun songListItemClicked(id: String) {
        _uiState.update { uiStateValue ->
            uiStateValue.copy(
                songListItems = uiStateValue.songListItems.map { songListItem ->
                    if (songListItem.id != id) return@map songListItem

                    return@map songListItem.copy(isSelected = !songListItem.isSelected)
                }
            )
        }
    }

    override fun searchFieldValueChanged(newValue: String) {
        _uiState.update { value ->
            value.copy(
                searchFieldValue = newValue,
                songListItems = value.songListItems.map { songListItem ->
                    songListItem.copy(
                        isVisible = newValue == "" || songListItem.name.contains(newValue, true),
                    )
                }
            )
        }
    }

    override fun selectAllCheckboxToggled(isSelected: Boolean) {
        _uiState.update { value ->
            value.copy(
                songListItems = value.songListItems.map { songListItem ->
                    songListItem.copy(isSelected = isSelected)
                },
            )
        }
    }

    override fun okButtonClicked() {
        viewModelScope.launch {
            val selectedSongs = _uiState.value.songListItems
                .filter { songListItem -> songListItem.isSelected }
                .map { songListItem -> songListItem.id }

            playlistApi.addSongs(playlistId, selectedSongs)

            _uiState.update { value -> value.copy(isDismissed = true) }
        }
    }

    override fun backButtonClicked() {
        _uiState.update { value -> value.copy(isDismissed = true) }
    }

    override val uiState: StateFlow<AddSongsToPlaylistPageUiState> get() = _uiState.asStateFlow()


    private val _uiState = MutableStateFlow(AddSongsToPlaylistPageUiState.initial())
}