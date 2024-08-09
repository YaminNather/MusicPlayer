package com.example.businesslogic.songplayersystem.songqueueapi.interactors

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songqueue.SongQueue
import com.example.businesslogic.songqueryapi.SongQueryApi

internal class GetCurrentSongInteractor(
    private val songQueue: SongQueue,
    private val songQueryApi: SongQueryApi,
) {
    public suspend fun get(): Song? {
        val currentSongId: String = songQueue.current ?: return null

        return songQueryApi.fetchWithId(currentSongId)
    }
}