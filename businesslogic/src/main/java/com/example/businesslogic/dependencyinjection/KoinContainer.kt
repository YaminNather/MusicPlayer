package com.example.businesslogic.dependencyinjection

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal class KoinContainer : KoinComponent {
    public inline fun <reified TType : Any> getValue(): TType {
        return get<TType>()
    }
}

internal val koinContainer: KoinContainer = KoinContainer()
