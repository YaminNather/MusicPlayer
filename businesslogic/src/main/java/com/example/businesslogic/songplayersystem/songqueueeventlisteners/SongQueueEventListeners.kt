package com.example.businesslogic.songplayersystem.songqueueeventlisteners

import com.example.businesslogic.apimodels.Song

internal class SongQueueEventListeners {
    public fun addCurrentSongUpdatedListener(listener: (song: Song?) -> Unit): Unit {
        _currentSongUpdated.add(listener)
    }

    public fun removeCurrentSongUpdatedListener(listener: (song: Song?) -> Unit): Unit {
        _currentSongUpdated.remove(listener)
    }

    public val currentSongUpdated: List<(song: Song?) -> Unit>
        get() = _currentSongUpdated



    private val _currentSongUpdated: MutableList<(song: Song?) -> Unit>
        = mutableListOf<(song: Song?) -> Unit>()
}