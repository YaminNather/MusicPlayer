package com.example.musicplayer.screens.maximizedsongplayer.viewmodel

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songplayerapi.SongPlayerApi
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi

import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState
import com.example.musicplayer.screens.songplayer.viewmodel.SongPlayerPageViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SongPlayerPageViewModelImpl(
    private val songQueueApi: SongQueueApi,
    private val songPlayerApi: SongPlayerApi,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
) : SongPlayerPageViewModel {
    public override fun pageOpened() {
        coroutineScope.launch {
            setCurrentSongInformation(songQueueApi.getCurrentSong()!!)
        }

        songQueueApi.addCurrentSongUpdatedListener(this::currentSongUpdatedListener)
        songPlayerApi.addProgressListener(this::songProgressListener)
    }

    public override fun playButtonClicked() {
        songPlayerApi.togglePauseState()
        _uiState.update { value -> value.copy(isPaused = !value.isPaused) }
    }

    public override fun previousSongButtonClicked() {
        songQueueApi.toPrevious()
    }

    public override fun nextSongButtonClicked() {
        songQueueApi.toNext()
    }


    public override fun progressSeeked(newProgress: Float) {
        songPlayerApi.seekToPosition(newProgress)
    }

    public override fun restartSongButtonClicked(): Unit {
        songPlayerApi.restart()
    }

    public override fun pageClosed(): Unit {
        songQueueApi.removeCurrentSongUpdatedListener(this::currentSongUpdatedListener)
        songPlayerApi.removeProgressListener(this::songProgressListener)
    }

    public override val uiState: StateFlow<SongPlayerPageUiState> get() = _uiState.asStateFlow()

    private fun currentSongUpdatedListener(song: Song?): Unit {
        if(song == null) return

        setCurrentSongInformation(song)
    }

    private fun songProgressListener(progress: Float): Unit {
        _uiState.update { value -> value.copy(songProgressFactor = progress) }
    }

    private fun setCurrentSongInformation(song: Song): Unit {
        _uiState.update { value ->
            value.copy(
                songName = song.name,
                artistName = song.artist,
                coverArt = song.coverArt,
                isPaused = !songPlayerApi.isPlaying,
            )
        }
    }



    private val _uiState = MutableStateFlow<SongPlayerPageUiState>(SongPlayerPageUiState.initial())

    private val coroutineScope: CoroutineScope = CoroutineScope(dispatcher)
}