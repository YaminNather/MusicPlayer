package com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songplayerapi.SongPlayerApi
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.uiState.MinimizedSongPlayerOverlayUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MinimizedSongPlayerOverlayViewModelImpl(
    private val songQueueApi: SongQueueApi,
    private val songPlayerApi: SongPlayerApi,
) : MinimizedSongPlayerOverlayViewModel {
    public override fun opened() {
        coroutineScope.launch {
            updateUiStateWithSongInformation(songQueueApi.getCurrentSong()!!)
        }

        songQueueApi.addCurrentSongUpdatedListener(this::currentPlayingChangedListener)
        songPlayerApi.addProgressListener(this::addProgressListener)
    }

    public override fun pauseButtonClicked() {
        songPlayerApi.togglePauseState()
        _uiState.update { value -> value.copy(isPlaying = songPlayerApi.isPlaying) }
    }

    public override fun toPreviousSongButtonClicked(): Unit {
        songQueueApi.toPrevious()
    }

    public override fun toNextSongButtonClicked(): Unit {
        songQueueApi.toNext()
    }

    public override fun closed() {
        songQueueApi.removeCurrentSongUpdatedListener(this::currentPlayingChangedListener)
        songPlayerApi.removeProgressListener(this::addProgressListener)
    }

    public override val uiState: StateFlow<MinimizedSongPlayerOverlayUiState>
        get() = _uiState.asStateFlow()

    private fun updateUiStateWithSongInformation(song: Song): Unit {
        _uiState.update { value -> value.copy(songName = song.name, artist = song.artist) }
    }

    private fun currentPlayingChangedListener(song: Song?) {
        if (song == null) return

        updateUiStateWithSongInformation(song)
    }

    private fun addProgressListener(value: Float): Unit {
        _uiState.update { uiStateValue -> uiStateValue.copy(progress = value) }
    }


    private val _uiState: MutableStateFlow<MinimizedSongPlayerOverlayUiState> =
        MutableStateFlow(MinimizedSongPlayerOverlayUiState.initial())


    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
}