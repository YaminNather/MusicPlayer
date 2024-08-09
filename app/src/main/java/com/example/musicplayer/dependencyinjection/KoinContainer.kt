package com.example.musicplayer.dependencyinjection

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

public class KoinContainer : KoinComponent {
    public inline fun <reified TType : Any> getValue(): TType {
        return get<TType>()
    }
}

public val koinContainer: KoinContainer = KoinContainer()