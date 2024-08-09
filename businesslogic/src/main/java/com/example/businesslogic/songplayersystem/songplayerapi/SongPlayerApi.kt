package com.example.businesslogic.songplayersystem.songplayerapi

import com.example.businesslogic.songplayersystem.songplayer.SongPlayer
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.AddProgressListenerInteractor
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.RemoveProgressListenerInteractor
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.SeekToPositionInteractor
import com.example.businesslogic.songplayersystem.songplayerapi.interactors.TogglePauseStateInteractor
import com.example.businesslogic.songplayersystem.songplayereventlisteners.SongPlayerEventListeners

public class SongPlayerApi internal constructor(
    private val togglePauseStateInteractor: TogglePauseStateInteractor,
    private val seekToPositionInteractor: SeekToPositionInteractor,
    private val addProgressListenerInteractor: AddProgressListenerInteractor,
    private val removeProgressListenerInteractor: RemoveProgressListenerInteractor,
    private val songPlayerEventListeners: SongPlayerEventListeners,
    private val songPlayer: SongPlayer,
) {
    public fun togglePauseState(): Unit {
        togglePauseStateInteractor.toggle()
    }

    public fun seekToPosition(position: Float): Unit {
        seekToPositionInteractor.seekTo(position)
    }

    public fun restart(): Unit {
        songPlayer.restart()
    }

    public fun addProgressListener(listener: (progress: Float) -> Unit): Unit {
        addProgressListenerInteractor.add(listener)
    }

    public fun removeProgressListener(listener: (progress: Float) -> Unit): Unit {
        removeProgressListenerInteractor.remove(listener)
    }

    public fun addSongCompletedListener(listener: () -> Unit): Unit {
        songPlayerEventListeners.songCompletedListeners.add(listener)
    }

    public fun removeSongCompletedListener(listener: () -> Unit): Unit {
        songPlayerEventListeners.songCompletedListeners.remove(listener)
    }

    public val isPlaying: Boolean get() = songPlayer.isPlaying

    public val progress: Float get() = songPlayer.progress
}