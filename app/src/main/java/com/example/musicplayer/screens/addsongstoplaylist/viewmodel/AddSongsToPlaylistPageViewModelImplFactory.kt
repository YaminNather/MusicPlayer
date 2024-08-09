package com.example.musicplayer.screens.addsongstoplaylist.viewmodel

import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.businesslogic.songqueryapi.SongQueryApi

internal class AddSongsToPlaylistPageViewModelImplFactory(
    private val songQueryApi: SongQueryApi,
    private val playlistApi: PlaylistApi,
) {
    fun build(playlistId: String): AddSongsToPlaylistPageViewModel {
        return AddSongsToPlaylistPageViewModelImpl(
            playlistId = playlistId,
            songQueryApi = songQueryApi,
            playlistApi = playlistApi,
        )
    }
}