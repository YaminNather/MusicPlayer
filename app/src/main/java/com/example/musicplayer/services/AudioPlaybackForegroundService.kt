package com.example.musicplayer.services

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

internal class AudioPlaybackForegroundService : MediaSessionService() {
    override fun onCreate() {
        super.onCreate()

        exoPlayer = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, exoPlayer!!).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onDestroy() {
        mediaSession?.release()
        mediaSession = null

        exoPlayer?.release()
        exoPlayer = null

        super.onDestroy()
    }


    private var exoPlayer: ExoPlayer? = null
    private var mediaSession: MediaSession? = null
}