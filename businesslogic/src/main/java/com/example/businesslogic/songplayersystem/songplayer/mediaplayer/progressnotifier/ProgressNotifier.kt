package com.example.businesslogic.songplayersystem.songplayer.mediaplayer.progressnotifier

import android.media.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class ProgressNotifier {
    public constructor(mediaPlayer: MediaPlayer) {
        this.mediaPlayer = mediaPlayer
        coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {
            while (true) {
                if (mediaPlayer.isPlaying) {
                    val position: Float =
                        mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration.toFloat()

                    listeners.forEach { listener -> listener(position) }
                }

                delay(1000);
            }
        }
    }

    public fun addListener(listener: ProgressListener) {
        listeners.add(listener)
    }

    public fun removeListener(listener: ProgressListener) {
        listeners.remove(listener)
    }



    private val mediaPlayer: MediaPlayer
    private val listeners: MutableList<ProgressListener> = mutableListOf<ProgressListener>()

    private val coroutineScope: CoroutineScope
}

public typealias ProgressListener = (progress: Float) -> Unit