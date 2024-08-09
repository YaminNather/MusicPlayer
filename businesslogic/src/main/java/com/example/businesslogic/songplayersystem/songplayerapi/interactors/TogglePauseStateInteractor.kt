package com.example.businesslogic.songplayersystem.songplayerapi.interactors

import com.example.businesslogic.songplayersystem.songplayer.SongPlayer

internal class TogglePauseStateInteractor(private val songPlayer: SongPlayer) {
    public fun toggle(): Unit {
        if (songPlayer.isPlaying) {
            songPlayer.pause()
        }
        else {
            songPlayer.unpause()
        }
    }
}