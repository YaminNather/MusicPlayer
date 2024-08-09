package com.example.businesslogic.eventsystem

internal class EventProducer {
    public fun add(event: Event): Unit {
        events.add(event)
    }

    public fun getAll(): List<Event> = events

    public fun clear(): Unit = events.clear()


    private val events: MutableList<Event> = mutableListOf<Event>()
}