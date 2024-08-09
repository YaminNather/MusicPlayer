package com.example.businesslogic.songqueryapi

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.querysongfetcher.QuerySongFetcher

public class SongQueryApi
    internal constructor(private val querySongFetcher: QuerySongFetcher) {
    public suspend fun fetchWithId(id: String): Song? {
        return querySongFetcher.fetchWithId(id)
    }

    public suspend fun getAll(): List<Song> {
        return querySongFetcher.getAll()
    }

    public suspend fun getMultiple(ids: List<String>): List<Song> {
        return querySongFetcher.getMultiple(ids)
    }

    public suspend fun getInAlbum(albumId: String): List<Song> {
        return querySongFetcher.getInAlbum(albumId)
    }

    public suspend fun getRecentlyPlayed(): List<Song> {
        return querySongFetcher.getRecentlyPlayed()
    }
}