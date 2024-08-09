package com.example.musicplayer.screens.playlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.businesslogic.apimodels.Playlist
import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class PlaylistPageViewModelImpl(
    private val id: String,
    private val playlistApi: PlaylistApi,
    private val songQueueApi: SongQueueApi,
) : ViewModel(), PlaylistPageViewModel {
    override fun pageOpened() {
        viewModelScope.launch {
            val playlist: Playlist = playlistApi.getWithId(id)!!
            val currentSong: Song? = songQueueApi.getCurrentSong()

            _uiState.update { value ->
                value.copy(
                    name = playlist.name,
                    coverArt = null,
                    songs = playlist.songs.map { element ->
                        SongListItemUiState(
                            id = element.id,
                            name = element.name,
                            artist = element.artist,
                            isPlaying = currentSong?.id == element.id,
                        )
                    }
                )
            }

            songQueueApi.addCurrentSongUpdatedListener(
                this@PlaylistPageViewModelImpl::currentSongUpdatedListener
            )
        }
    }

    override fun songListItemClicked(id: String) {
        songQueueApi.clear()

        val songIndex: Int = _uiState.value.songs.indexOfFirst { element -> element.id == id }

        _uiState.value.songs.subList(songIndex, _uiState.value.songs.size).forEach { song ->
            songQueueApi.addAsNext(song.id)
        }

        _uiState.value.songs.subList(0, songIndex).forEach { song ->
            songQueueApi.addAsNext(song.id)
        }
    }

    override fun addSongsButtonClicked() {
        _uiState.update { value -> value.copy(isAddSongToPlaylistPageOpen = true) }
    }

    override fun addSongsToPlaylistPageDismissed() {
        _uiState.update { value -> value.copy(isAddSongToPlaylistPageOpen = false) }
    }

    override fun addSongsToPlaylistPageReturnedResult(result: Boolean) {
        _uiState.update { value ->
            value.copy(
                isAddSongToPlaylistPageOpen = false,
                pendingNotifications =
                    if (result) value.pendingNotifications + "Successfully added to playlist"
                    else value.pendingNotifications,
            )
        }
    }

    override fun pendingNotificationsDisplayed() {
        _uiState.update { value -> value.copy(pendingNotifications = listOf()) }
    }

    override fun backButtonClicked() {
        _uiState.update { value -> value.copy(isNavigatingBack = true) }
    }

    override fun pageClosed() {
        songQueueApi.removeCurrentSongUpdatedListener(this::currentSongUpdatedListener)
    }

    override val uiState: StateFlow<PlaylistPageUiState> get() = _uiState.asStateFlow()

    private fun currentSongUpdatedListener(song: Song?): Unit {
        _uiState.update { value ->
            value.copy(
                songs = value.songs.map { songListItem ->
                    songListItem.copy(isPlaying = songListItem.id == song?.id)
                }
            )
        }
    }



    private val _uiState: MutableStateFlow<PlaylistPageUiState>
        = MutableStateFlow<PlaylistPageUiState>(PlaylistPageUiState.initial())
}