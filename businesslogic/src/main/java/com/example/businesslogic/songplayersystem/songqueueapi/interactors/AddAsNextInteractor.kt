package com.example.businesslogic.songplayersystem.songqueueapi.interactors

import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.songplayersystem.songqueue.SongQueue

internal class AddAsNextInteractor(
    private val songQueue: SongQueue,
    private val eventPublisher: EventPublisher,
) {
    public fun add(id: String): Unit {
        songQueue.addAsNext(id)

        eventPublisher.publish(songQueue.eventProducer)
    }
}