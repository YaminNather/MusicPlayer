package com.example.businesslogic.songplayersystem.songplayer.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import com.example.businesslogic.eventsystem.EventPublisher
import com.example.businesslogic.songplayersystem.songplayer.SongPlayer
import com.example.businesslogic.songplayersystem.songplayer.mediaplayer.progressnotifier.ProgressNotifier

internal class MediaPlayerSongPlayerFactory(
    private val context: Context,
    private val eventPublisher: EventPublisher,
) {
    public fun build(): SongPlayer {
        val mediaPlayer: MediaPlayer =
            if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) MediaPlayer(context)
            else MediaPlayer()


        return MediaPlayerSongPlayer(
            mediaPlayer = mediaPlayer,
            progressNotifier = ProgressNotifier(mediaPlayer),
            context = context,
            eventPublisher = eventPublisher
        )
    }

}