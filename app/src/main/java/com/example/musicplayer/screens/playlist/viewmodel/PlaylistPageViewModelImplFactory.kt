package com.example.musicplayer.screens.playlist.viewmodel

import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi

internal class PlaylistPageViewModelImplFactory(
    private val playlistApi: PlaylistApi,
    private val songQueueApi: SongQueueApi,
) {
    public fun build(id: String): PlaylistPageViewModelImpl {
        return PlaylistPageViewModelImpl(
            id = id,
            playlistApi = playlistApi,
            songQueueApi = songQueueApi,
        )
    }
}