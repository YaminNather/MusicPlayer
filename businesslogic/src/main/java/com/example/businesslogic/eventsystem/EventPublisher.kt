package com.example.businesslogic.eventsystem

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class EventPublisher {
    public inline fun <reified TEvent : Event> registerEventHandler(
        eventHandler: EventHandler<TEvent>
    ): Unit {
        if (!eventHandlers.containsKey(TEvent::class)) {
            eventHandlers[TEvent::class] = mutableListOf<Any>()
        }

        eventHandlers[TEvent::class]!!.add(eventHandler)
    }

    public fun publish(vararg events: Event): Unit {
        for (event in events) {
            for (aEventHandler in eventHandlers[event::class]!!) {
                val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    val eventHandler: EventHandler<Event> = aEventHandler as EventHandler<Event>
                    eventHandler.handle(event)
                }
            }
        }
    }

    public fun publish(eventProducer: EventProducer) {
        publish(*eventProducer.getAll().toTypedArray())
        eventProducer.clear()
    }

    private val eventHandlers: MutableMap<Any, MutableList<Any>> = mutableMapOf()
}