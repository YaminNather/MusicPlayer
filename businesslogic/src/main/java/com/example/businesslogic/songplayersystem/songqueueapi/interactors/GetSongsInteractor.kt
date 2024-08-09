package com.example.businesslogic.songplayersystem.songqueueapi.interactors

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.querysongfetcher.QuerySongFetcher
import com.example.businesslogic.songplayersystem.songqueue.SongQueue
import com.example.businesslogic.songqueryapi.SongQueryApi

internal class GetSongsInteractor(
    private val songQueue: SongQueue,
    private val songFetcher: QuerySongFetcher,
) {
    public suspend fun get(): List<Song> {
        val songs: List<Song> = songFetcher.getMultiple(ids = songQueue.getAll())
        val mappedSongs: MutableMap<String, Song> = mutableMapOf<String, Song>()

        for (song in songs) {
            mappedSongs[song.id] = song
        }

        return songQueue.getAll()
            .map { songId -> mappedSongs[songId]!! }
    }
}