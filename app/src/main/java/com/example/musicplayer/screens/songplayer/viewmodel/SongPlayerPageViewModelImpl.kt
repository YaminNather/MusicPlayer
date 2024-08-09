package com.example.musicplayer.screens.songplayer.viewmodel

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songplayerapi.SongPlayerApi
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi

import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

internal class SongPlayerPageViewModelImpl(
    private val songQueueApi: SongQueueApi,
    private val songPlayerApi: SongPlayerApi,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
) : SongPlayerPageViewModel {
    public override fun pageOpened() {
        coroutineScope.launch {
            setCurrentSongInformation(songQueueApi.getCurrentSong()!!)
        }

        songPlayerApi.addProgressListener(this::songProgressListener)
        _uiState.update { value -> value.copy(isPaused = !songPlayerApi.isPlaying) }

        songQueueApi.addCurrentSongUpdatedListener(this::currentSongUpdatedListener)
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
        _uiState.update { value ->
            value.copy(songProgressFactor = newProgress)
        }
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
        _uiState.update { value ->
            value.copy(
                songProgressFactor = progress,
                songProgress = (uiState.value.songDuration.inWholeSeconds / progress.toDouble()).seconds,
            )
        }
    }

    private fun setCurrentSongInformation(song: Song): Unit {
        _uiState.update { value ->
            value.copy(
                songName = song.name,
                artistName = song.artist,
                coverArt = song.coverArt,
                songDuration = song.duration,
                isPaused = !songPlayerApi.isPlaying,
            )
        }
    }



    private val _uiState = MutableStateFlow<SongPlayerPageUiState>(SongPlayerPageUiState.initial())

    private val coroutineScope: CoroutineScope = CoroutineScope(dispatcher)
}