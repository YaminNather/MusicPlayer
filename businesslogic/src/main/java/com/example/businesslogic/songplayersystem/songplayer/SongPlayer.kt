package com.example.businesslogic.songplayersystem.songplayer

import android.net.Uri

internal interface SongPlayer {
    public fun unpause(): Unit

    public fun pause(): Unit

    public fun seekTo(position: Float): Unit

    public fun restart(): Unit

    public fun setSong(uri: Uri): Unit

    public fun reset(): Unit

    public val isPlaying: Boolean

    public val progress: Float

    public fun addProgressListener(listener: (value: Float) -> Unit): Unit

    public fun removeProgressListener(listener: (value: Float) -> Unit): Unit
}