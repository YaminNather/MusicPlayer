package com.example.musicplayer.components.songplayeroverlay.viewmodel

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.internalsong.InternalSong
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.musicplayer.components.songplayeroverlay.SongPlayerType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class SongPlayerOverlayViewModel(
    private val songQueueApi: SongQueueApi,
) {
    public fun opened(): Unit {
        songQueueApi.addCurrentSongUpdatedListener(this::currentSongUpdatedListener)
    }

    public fun songPlayerTypeChanged(newValue: SongPlayerType): Unit {
        _uiState.update { state -> state.copy(songPlayerType = newValue) }
    }

    public fun closed(): Unit {
        songQueueApi.removeCurrentSongUpdatedListener(this::currentSongUpdatedListener)
    }

    private fun currentSongUpdatedListener(song: Song?): Unit {
        _uiState.update { value ->
            value.copy(
                isQueueEmpty = song == null,
                songPlayerType =
                    if(song == null) {
                        null
                    }
                    else {
                        value.songPlayerType ?: SongPlayerType.Minimized
                    }
            )
        }
    }

    public val uiState: StateFlow<SongPlayerOverlayUiState> get() = _uiState.asStateFlow()




    private val _uiState: MutableStateFlow<SongPlayerOverlayUiState> =
        MutableStateFlow(SongPlayerOverlayUiState())
}

internal data class SongPlayerOverlayUiState(
    public val isQueueEmpty: Boolean = true,
    public val songPlayerType: SongPlayerType? = null,
)