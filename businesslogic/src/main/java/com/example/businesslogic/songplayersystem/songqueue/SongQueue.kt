package com.example.businesslogic.songplayersystem.songqueue

import com.example.businesslogic.eventsystem.EventProducer
import com.example.businesslogic.songplayersystem.songqueue.events.CurrentSongUpdatedEvent

internal class SongQueue {
    public fun addToEnd(id: String): Unit {
        songIds.add(id)

        if (songIds.size == 1) {
            _currentIndex = 0
            eventProducer.add(CurrentSongUpdatedEvent(id))
        }
    }

    public fun addAsNext(id: String): Unit {
        if (songIds.size == 0) {
            songIds.add(id)
            _currentIndex = 0
            eventProducer.add( CurrentSongUpdatedEvent(id) )
        }
        else if (_currentIndex != songIds.size - 1) {
            songIds.add(_currentIndex!! + 1, id)
        }
        else {
            songIds.add(id)
        }
    }

    public fun pop(index: Int): Unit {
        if (songIds.size == 0 || index < 0 || index > songIds.size - 1) return

        if (songIds.size == 1) {
            songIds.removeAt(0)
            _currentIndex = null
            eventProducer.add( CurrentSongUpdatedEvent(null) )
        }
        else if (index < _currentIndex!!) {
            songIds.removeAt(index)
            _currentIndex = _currentIndex!! - 1
        }
        else if (index == _currentIndex) {
            songIds.removeAt(index)
            _currentIndex = songIds.size - 1

            eventProducer.add(CurrentSongUpdatedEvent(songIds[_currentIndex!!]))
        }
        else {
            songIds.removeAt(index)
        }
    }

    public fun clear(): Unit {
        songIds.clear()
        _currentIndex = null

        eventProducer.add( CurrentSongUpdatedEvent(null) )
    }

    public fun toPrevious(): Unit {
        if (songIds.size == 0) {
            return
        }

        if (_currentIndex == 0) {
            _currentIndex = songIds.size - 1
        }
        else {
            _currentIndex = _currentIndex!! - 1
        }

        eventProducer.add( CurrentSongUpdatedEvent(songIds[_currentIndex!!]) )
    }

    public fun toNext(): Unit {
        if (songIds.size == 0) {
            return
        }

        if (_currentIndex != songIds.size - 1) {
            _currentIndex = _currentIndex!! + 1
        }
        else {
            _currentIndex = 0
        }

        eventProducer.add( CurrentSongUpdatedEvent(songIds[_currentIndex!!]) )
    }

    public fun setCurrent(index: Int): Unit {
        if (index < 0 || index > songIds.size - 1) {
            throw IndexOutOfBoundsException()
        }

        if (index == _currentIndex) return

        _currentIndex = index
        eventProducer.add( CurrentSongUpdatedEvent(songIds[_currentIndex!!]) )
    }

    public val currentIndex: Int? get() = _currentIndex

    public val current: String? get() {
        val currentlyPlayingIndex: Int? = this._currentIndex

        return if (currentlyPlayingIndex == null) {
            null
        }
        else {
            songIds[currentlyPlayingIndex]
        }
    }

    public operator fun get(index: Int): String = songIds[index]

    public fun getAll(): List<String> = songIds.toList()

    public val size: Int get() = songIds.size

    public val eventProducer: EventProducer = EventProducer()


    private val songIds: MutableList<String> = mutableListOf<String>()
    private var _currentIndex: Int? = null

}
