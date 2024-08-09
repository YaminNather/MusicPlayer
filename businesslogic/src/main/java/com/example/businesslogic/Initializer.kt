package com.example.businesslogic

import com.example.businesslogic.dependencyinjection.koinContainer
import com.example.businesslogic.eventsystem.EventHandler
import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.songplayersystem.eventhandlers.completedsong.NotifyExternalListenersEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.completedsong.ToNextSongInQueueEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.completedsong.UpdateLastPlayedAtEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.currentsongupdated.NotifyExternalListenersOnCurrentSongUpdatedEventHandler
import com.example.businesslogic.songplayersystem.eventhandlers.currentsongupdated.PlayNewSongOnCurrentSongUpdatedEventHandler
import com.example.businesslogic.songplayersystem.songplayer.events.SongCompletedEvent
import com.example.businesslogic.songplayersystem.songqueue.events.CurrentSongUpdatedEvent

public class Initializer {
    public fun initialize(): Unit {
        val eventPublisher: EventPublisher = koinContainer.getValue<EventPublisher>()

        val currentSongUpdatedEventHandlers: List<EventHandler<CurrentSongUpdatedEvent>> = listOf(
            koinContainer.getValue<PlayNewSongOnCurrentSongUpdatedEventHandler>(),
            koinContainer.getValue<NotifyExternalListenersOnCurrentSongUpdatedEventHandler>(),
        )

        currentSongUpdatedEventHandlers.forEach { eventHandler ->
            eventPublisher.registerEventHandler<CurrentSongUpdatedEvent>(eventHandler)
        }

        val songCompletedEventHandlers: List<EventHandler<SongCompletedEvent>> = listOf(
            koinContainer.getValue<UpdateLastPlayedAtEventHandler>(),
            koinContainer.getValue<ToNextSongInQueueEventHandler>(),
            koinContainer.getValue<NotifyExternalListenersEventHandler>(),
        )

        songCompletedEventHandlers.forEach { eventHandler ->
            eventPublisher.registerEventHandler<SongCompletedEvent>(eventHandler)
        }
    }
}
