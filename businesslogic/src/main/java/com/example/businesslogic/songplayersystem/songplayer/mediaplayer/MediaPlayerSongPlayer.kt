package com.example.businesslogic.songplayersystem.songplayer.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.songplayersystem.songplayer.SongPlayer
import com.example.businesslogic.songplayersystem.songplayer.events.SongCompletedEvent
import com.example.businesslogic.songplayersystem.songplayer.mediaplayer.progressnotifier.ProgressNotifier

internal class MediaPlayerSongPlayer(
    private val mediaPlayer: MediaPlayer,
    private val eventPublisher: EventPublisher,
    private val progressNotifier: ProgressNotifier,
    private val context: Context,
) : SongPlayer {
    init {
        mediaPlayer.setOnCompletionListener() { eventPublisher.publish(SongCompletedEvent()) }
    }

    public override fun pause(): Unit {
        mediaPlayer.pause()
    }

    public override fun unpause(): Unit {
        mediaPlayer.start()
    }

    public override fun seekTo(position: Float): Unit {
        mediaPlayer.seekTo((mediaPlayer.duration * position).toInt())
    }

    public override fun restart(): Unit {
        mediaPlayer.seekTo(0)
    }

    public override fun setSong(uri: Uri) {
        mediaPlayer.reset()

        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.prepare()

        mediaPlayer.start()
    }

    public override fun reset(): Unit {
        mediaPlayer.reset()
    }

    public override val isPlaying: Boolean get() = mediaPlayer.isPlaying

    public override val progress: Float get() {
        return (mediaPlayer.currentPosition / mediaPlayer.duration).toFloat()
    }

    public override fun addProgressListener(listener: ProgressListener): Unit {
        progressNotifier.addListener(listener)
    }

    public override fun removeProgressListener(listener: ProgressListener): Unit {
        progressNotifier.removeListener(listener)
    }
}

typealias ProgressListener = (value: Float) -> Unit