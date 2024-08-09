package com.example.businesslogic.songplayersystem.songplayerapi.interactors

import com.example.businesslogic.songplayersystem.songplayer.SongPlayer

internal class RemoveProgressListenerInteractor(private val songPlayer: SongPlayer) {
    public fun remove(listener: (value: Float) -> Unit): Unit {
        songPlayer.removeProgressListener(listener)
    }
}