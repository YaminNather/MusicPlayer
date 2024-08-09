package com.example.businesslogic.songplayersystem.eventhandlers.completedsong

import com.example.businesslogic.eventsystem.EventHandler
import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.songplayersystem.songplayer.events.SongCompletedEvent
import com.example.businesslogic.songplayersystem.songqueue.SongQueue

internal class ToNextSongInQueueEventHandler(
    private val songQueue: SongQueue,
    private val eventPublisher: EventPublisher,
) : EventHandler<SongCompletedEvent> {
    public override suspend fun handle(event: SongCompletedEvent): Unit {
        songQueue.toNext()
        eventPublisher.publish(songQueue.eventProducer)
    }
}