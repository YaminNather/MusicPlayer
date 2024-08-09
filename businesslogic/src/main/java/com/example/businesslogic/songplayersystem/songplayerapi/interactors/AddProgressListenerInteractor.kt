package com.example.businesslogic.songplayersystem.songplayerapi.interactors

import com.example.businesslogic.songplayersystem.songplayer.SongPlayer

internal class AddProgressListenerInteractor(private val songPlayer: SongPlayer) {
    public fun add(listener: (value: Float) -> Unit): Unit {
        songPlayer.addProgressListener(listener)
    }
}