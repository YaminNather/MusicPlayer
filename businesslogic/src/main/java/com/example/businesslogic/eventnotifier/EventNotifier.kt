package com.example.businesslogic.eventnotifier

public class EventNotifier<TEvent> {
    public fun notify(event: TEvent): Unit {
        listeners.forEach { listener -> listener(event) }
    }

    public fun addListener(listener: (event: TEvent) -> Unit): Unit {
        listeners.add(listener)
    }

    public fun removeListener(listener: (event: TEvent) -> Unit): Unit {
        listeners.remove(listener)
    }


    private val listeners: MutableList<(event: TEvent) -> Unit> = mutableListOf()
}