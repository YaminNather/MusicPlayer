package com.example.musicplayer.screens.album.viewmodel

import com.example.businesslogic.albumqueryapi.AlbumQueryApi
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.businesslogic.songqueryapi.SongQueryApi

internal class AlbumPageViewModelFactory(
    private val albumQueryApi: AlbumQueryApi,
    private val songQueryApi: SongQueryApi,
    private val songQueueApi: SongQueueApi,
) {
    public fun build(id: String): AlbumPageViewModel {
        return AlbumPageViewModelImpl(
            id,
            albumQueryApi = albumQueryApi,
            songQueryApi = songQueryApi,
            songQueueApi = songQueueApi,
        )
    }
}