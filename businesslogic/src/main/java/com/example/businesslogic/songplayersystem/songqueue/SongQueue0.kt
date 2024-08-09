package com.example.businesslogic.songplayersystem.songqueue

import com.example.businesslogic.eventsystem.EventProducer
import com.example.businesslogic.songplayersystem.songqueue.events.CurrentSongUpdatedEvent

internal class SongQueue0 {
    public fun popPlaying(): Unit {
        songIds.removeAt(0)

        val playing: String? = if (songIds.size != 0) songIds[0] else null
        eventProducer.add(CurrentSongUpdatedEvent(playing))
    }

    public fun addToEnd(id: String): Unit {
        songIds.add(id)

        if (songIds.size == 1) {
            eventProducer.add(CurrentSongUpdatedEvent(id))
        }
    }

    public fun addToStart(id: String): Unit {
        songIds.add(1, id)

        if (songIds.size == 1) {
            eventProducer.add(CurrentSongUpdatedEvent(id))
        }
    }

    public fun setCurrent(id: String): Unit {
        if (songIds.size != 0) {
            songIds.removeAt(0)
        }

        songIds.add(0, id)

        eventProducer.add(CurrentSongUpdatedEvent(id))
    }

    public val current: String? get() = if (songIds.isNotEmpty()) songIds[0] else null

    public operator fun get(index: Int): String = songIds[index]

    public fun getAll(): List<String> = songIds.toList()

    public val size: Int get() = songIds.size


    private val songIds: MutableList<String> = mutableListOf<String>()

    public val eventProducer: EventProducer = EventProducer()
}
