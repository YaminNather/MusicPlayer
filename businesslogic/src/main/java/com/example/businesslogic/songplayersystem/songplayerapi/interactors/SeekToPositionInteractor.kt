package com.example.businesslogic.songplayersystem.songplayerapi.interactors

import com.example.businesslogic.songplayersystem.songplayer.SongPlayer

internal class SeekToPositionInteractor(private val songPlayer: SongPlayer) {
    public fun seekTo(position: Float) {
        songPlayer.seekTo(position)
    }
}