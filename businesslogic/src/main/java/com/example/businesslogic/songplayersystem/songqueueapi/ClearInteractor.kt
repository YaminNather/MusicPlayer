package com.example.businesslogic.songplayersystem.songqueueapi

import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.songplayersystem.songqueue.SongQueue

internal class ClearInteractor(
    private val songQueue: SongQueue,
    private val eventPublisher: EventPublisher,
) {
    public fun clear(): Unit {
        songQueue.clear()
        eventPublisher.publish(songQueue.eventProducer)
    }
}