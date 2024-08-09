package com.example.businesslogic.songplayersystem.eventhandlers.completedsong

import com.example.businesslogic.eventsystem.EventHandler
import com.example.businesslogic.internalsong.InternalSong
import com.example.businesslogic.internalsong.songfetcher.SongRepository
import com.example.businesslogic.songplayersystem.songplayer.events.SongCompletedEvent
import com.example.businesslogic.songplayersystem.songqueue.SongQueue

internal class UpdateLastPlayedAtEventHandler(
    private val songRepository: SongRepository,
    private val songQueue: SongQueue,
) : EventHandler<SongCompletedEvent> {
    public override suspend fun handle(event: SongCompletedEvent) {
        val song: InternalSong = songRepository.fetchWithId(songQueue.current!!)!!

        song.updateLastPlayed()

        songRepository.save(song)
    }

}