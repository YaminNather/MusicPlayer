package com.example.businesslogic.songplayersystem.eventhandlers.completedsong

import com.example.businesslogic.eventsystem.EventHandler
import com.example.businesslogic.songplayersystem.songplayer.events.SongCompletedEvent
import com.example.businesslogic.songplayersystem.songplayereventlisteners.SongPlayerEventListeners

internal class NotifyExternalListenersEventHandler(
    private val songPlayerEventListeners: SongPlayerEventListeners,
) : EventHandler<SongCompletedEvent> {
    public override suspend fun handle(event: SongCompletedEvent): Unit {
        songPlayerEventListeners.songCompletedListeners.forEach { listener -> listener() }
    }
}