package com.example.businesslogic.songplayersystem.songqueueapi

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.songplayersystem.songqueue.SongQueue
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.AddToEndInteractor
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.AddAsNextInteractor
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.GetCurrentSongInteractor
import com.example.businesslogic.songplayersystem.songqueueapi.interactors.GetSongsInteractor
import com.example.businesslogic.songplayersystem.songqueueeventlisteners.SongQueueEventListeners

public class SongQueueApi internal constructor (
    private val songQueue: SongQueue,
    private val eventPublisher: EventPublisher,

    private val addAsNextInteractor: AddAsNextInteractor,
    private val addToEndInteractor: AddToEndInteractor,
    private val clearInteractor: ClearInteractor,

    private val getSongsInteractor: GetSongsInteractor,
    private val getCurrentSongInteractor: GetCurrentSongInteractor,

    private val songQueueEventListeners: SongQueueEventListeners,
) {
    public fun addAsNext(id: String): Unit {
        addAsNextInteractor.add(id)
    }

    public fun addToEnd(id: String): Unit {
        addToEndInteractor.add(id)
    }

    public fun pop(index: Int): Unit {
        songQueue.pop(index)
        eventPublisher.publish(songQueue.eventProducer)
    }

    public fun setCurrent(index: Int): Unit {
        songQueue.setCurrent(index)
        eventPublisher.publish(songQueue.eventProducer)
    }

    public fun toPrevious(): Unit {
        songQueue.toPrevious()
        eventPublisher.publish(songQueue.eventProducer)
    }

    public fun toNext(): Unit {
        songQueue.toNext()
        eventPublisher.publish(songQueue.eventProducer)
    }

    public fun clear(): Unit {
        clearInteractor.clear()
    }

    public suspend fun getSongs(): List<Song> {
        return getSongsInteractor.get()
    }

    public fun getCurrentSongIndex(): Int? {
        return songQueue.currentIndex
    }

    public suspend fun getCurrentSong(): Song? {
        return getCurrentSongInteractor.get()
    }

    public fun addCurrentSongUpdatedListener(listener: (song: Song?) -> Unit): Unit {
        songQueueEventListeners.addCurrentSongUpdatedListener(listener)
    }

    public fun removeCurrentSongUpdatedListener(listener: (song: Song?) -> Unit): Unit {
        songQueueEventListeners.removeCurrentSongUpdatedListener(listener)
    }
}
