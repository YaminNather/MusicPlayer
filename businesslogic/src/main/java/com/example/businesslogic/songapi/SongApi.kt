package com.example.businesslogic.songapi

import com.example.businesslogic.internalsong.InternalSong
import com.example.businesslogic.internalsong.songfetcher.SongRepository

public class SongApi internal constructor(private val songRepository: SongRepository) {
    public suspend fun like(id: String): Unit {
        val song: InternalSong = songRepository.fetchWithId(id)!!
        song.like()
        songRepository.save(song)
    }

    public suspend fun unlike(id: String): Unit {
        val song: InternalSong = songRepository.fetchWithId(id)!!
        song.unlike()
        songRepository.save(song)
    }
}