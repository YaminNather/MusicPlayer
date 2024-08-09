package com.example.businesslogic.internalsong

import java.util.Calendar
import java.util.Date

public class InternalSong(
    public val id: String,
    public val uri: String,
    isLiked: Boolean,
    lastPlayed: Date,
) {
    public fun like(): Unit {
        _isLiked = true
    }

    public fun unlike(): Unit {
        _isLiked = false
    }

    public fun updateLastPlayed(): Unit {
        _lastPlayed = Calendar.getInstance().time
    }

    public val isLiked: Boolean get() = _isLiked

    public val lastPlayedAt: Date get() = _lastPlayed



    private var _isLiked: Boolean = isLiked
    private var _lastPlayed: Date = lastPlayed
}