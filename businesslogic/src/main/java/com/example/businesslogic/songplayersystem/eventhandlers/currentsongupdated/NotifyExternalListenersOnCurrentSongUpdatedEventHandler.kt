package com.example.businesslogic.songplayersystem.eventhandlers.currentsongupdated

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.eventsystem.EventHandler
import com.example.businesslogic.querysongfetcher.QuerySongFetcher
import com.example.businesslogic.songplayersystem.songqueue.events.CurrentSongUpdatedEvent
import com.example.businesslogic.songplayersystem.songqueueeventlisteners.SongQueueEventListeners

internal class NotifyExternalListenersOnCurrentSongUpdatedEventHandler(
    private val songFetcher: QuerySongFetcher,
    private val songQueueEventListeners: SongQueueEventListeners,
) : EventHandler<CurrentSongUpdatedEvent> {
    public override suspend fun handle(event: CurrentSongUpdatedEvent): Unit {
        if (event.id == null) {
            songQueueEventListeners.currentSongUpdated.forEach { listener -> listener(null) }
        }
        else {
            val song: Song? = songFetcher.fetchWithId(event.id)!!
            songQueueEventListeners.currentSongUpdated.forEach { listener -> listener(song) }
        }
    }
}