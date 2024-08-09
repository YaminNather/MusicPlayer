package com.example.businesslogic.songplayersystem.eventhandlers.currentsongupdated

import android.net.Uri
import com.example.businesslogic.internalsong.InternalSong
import com.example.businesslogic.internalsong.songfetcher.SongRepository
import com.example.businesslogic.eventsystem.EventHandler
import com.example.businesslogic.songplayersystem.songplayer.SongPlayer
import com.example.businesslogic.songplayersystem.songqueue.events.CurrentSongUpdatedEvent

internal class PlayNewSongOnCurrentSongUpdatedEventHandler(
    private val songPlayer: SongPlayer,
    private val songRepository: SongRepository,
): EventHandler<CurrentSongUpdatedEvent> {
    public override suspend fun handle(event: CurrentSongUpdatedEvent): Unit {
        if (event.id != null) {
            val song: InternalSong = songRepository.fetchWithId(event.id)!!
            songPlayer.setSong(Uri.parse(song.uri))
            songPlayer.unpause()
        }
        else {
            songPlayer.reset()
        }
    }
}