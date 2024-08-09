package com.example.musicplayer.screens.songqueue.viewmodel

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.musicplayer.screens.songqueue.viewmodel.uistate.SongQueueItemUiState
import com.example.musicplayer.screens.songqueue.viewmodel.uistate.SongQueuePageUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SongQueuePageViewModelImpl(
    private val songQueueApi: SongQueueApi,
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main,
) : SongQueuePageViewModel {
    public override fun pageOpened(): Unit {
        coroutineScope.launch {
            val queueSongs: List<Song> = songQueueApi.getSongs()
            val currentSongIndex: Int? = songQueueApi.getCurrentSongIndex()

            _uiState.update { value ->
                value.updateUiStateWithFetchedSongs(queueSongs, currentSongIndex ?: 0)
            }
        }

        songQueueApi.addCurrentSongUpdatedListener(this::currentSongUpdatedListener)
    }

    public override fun queueItemClicked(id: Int): Unit {
        songQueueApi.setCurrent(id)
    }

    public override fun queueItemMoreOptionsButtonClicked(id: Int): Unit {
        _uiState.update { current ->
            current.copy(
                songQueueItems = current.songQueueItems.map { element ->
                    if (element.id != id) return@map element

                    return@map element.copy(isMoreOptionsMenuOpen = true)
                }
            )
        }
    }

    public override fun queueItemMoreOptionsMenuDismissed(id: Int): Unit {
        _uiState.update { current -> current.closeQueueItemsMoreOptionsMenu(id) }
    }

    public override fun removeQueueItemButtonClicked(id: Int): Unit  {
        songQueueApi.pop(id)

        coroutineScope.launch {
            val songs: List<Song> = songQueueApi.getSongs()
            val currentSongIndex: Int? = songQueueApi.getCurrentSongIndex()

            _uiState.update { current ->
                current
                    .updateUiStateWithFetchedSongs(songs, currentSongIndex ?: 0)
                    .closeQueueItemsMoreOptionsMenu(id)
            }
        }
    }

    public override fun closed(): Unit {
        songQueueApi.removeCurrentSongUpdatedListener(this::currentSongUpdatedListener)
    }

    private fun SongQueuePageUiState.closeQueueItemsMoreOptionsMenu( id: Int): SongQueuePageUiState {
        return this.copy(
            songQueueItems = this.songQueueItems.map { element ->
                if (element.id != id) return@map element

                return@map element.copy(isMoreOptionsMenuOpen = false)
            }
        )
    }

    private fun SongQueuePageUiState.updateUiStateWithFetchedSongs(
        songs: List<Song>,
        currentSongIndex: Int,
    ): SongQueuePageUiState {
        return this.copy(
            songQueueItems = songs
                .mapIndexed { index, element ->
                    SongQueueItemUiState(
                        id = index,
                        name = element.name,
                        artist = element.artist,
                        isPlaying = index == currentSongIndex,
                        isMoreOptionsMenuOpen = false,
                    )
                },
        )
    }

    public override val uiState: StateFlow<SongQueuePageUiState> get() = _uiState.asStateFlow()

    private fun currentSongUpdatedListener(song: Song?): Unit {
        val currentSongIndex: Int? = songQueueApi.getCurrentSongIndex()

        _uiState.update { value ->
            value.copy(
                songQueueItems = value.songQueueItems.mapIndexed { index, element ->
                    element.copy(isPlaying = index == currentSongIndex)
                }
            )
        }
    }



    private val _uiState: MutableStateFlow<SongQueuePageUiState> =
        MutableStateFlow(SongQueuePageUiState.initial())

    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineDispatcher)
}