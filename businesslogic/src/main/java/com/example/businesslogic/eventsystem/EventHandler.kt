package com.example.businesslogic.eventsystem

public interface EventHandler<TEvent : Event> {
    public suspend fun handle(event: TEvent): Unit
}