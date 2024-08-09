package com.example.musicplayer.screens.album.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.businesslogic.albumqueryapi.AlbumQueryApi
import com.example.businesslogic.apimodels.Album
import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.businesslogic.songqueryapi.SongQueryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AlbumPageViewModelImpl(
    private val id: String,
    private val albumQueryApi: AlbumQueryApi,
    private val songQueryApi: SongQueryApi,
    private val songQueueApi: SongQueueApi,
) : AlbumPageViewModel {
    @RequiresApi(Build.VERSION_CODES.Q)
    public override fun pageOpened(): Unit {
        coroutineScope.launch {
            songQueueApi.addCurrentSongUpdatedListener(this@AlbumPageViewModelImpl::currentSongUpdatedListener)

            val deferredAlbumQuery: Deferred<Album> = async { albumQueryApi.getById(id)!! }
            val deferredSongsQuery: Deferred<List<Song>> = async { songQueryApi.getInAlbum(id) }

            val album: Album = deferredAlbumQuery.await()
            val songs: List<Song> = deferredSongsQuery.await()
            val currentlyPlayingSong: Song? = songQueueApi.getCurrentSong()

            _uiState.update { value ->
                value.copy(
                    id = album.id,
                    name = album.name,
                    artist = album.artist,
                    coverArt = album.coverArt,
                    songListItems = songs.map { song ->
                        SongListItemUiState(
                            id = song.id,
                            name = song.name,
                            artist = song.artist,
                            isPlaying =
                                if (currentlyPlayingSong != null) song.id == currentlyPlayingSong.id
                                else false,
                        )
                    }
                )
            }
        }
    }

    public override fun songListItemClicked(id: String) {
        songQueueApi.clear()
        songQueueApi.addAsNext(id)
    }

    public override fun backButtonClicked(): Unit {
        _uiState.update { value -> value.copy(isPoppingPage = true) }
    }

    public override fun pageClosed(): Unit {
        songQueueApi.removeCurrentSongUpdatedListener(this::currentSongUpdatedListener)
    }

    public override val uiState: StateFlow<AlbumPageUiState> get() = _uiState.asStateFlow()

    private fun currentSongUpdatedListener(song: Song?): Unit {
        _uiState.update { value ->
            value.copy(
                isPlaying =
                    if (song != null)
                        value.songListItems.find { element -> element.id == song.id } != null
                    else false,
                songListItems = value.songListItems.map { songListItem ->
                    return@map songListItem.copy(
                        isPlaying = if (song != null) song.id == songListItem.id else false,
                    )
                },
            )
        }
    }

    private val _uiState: MutableStateFlow<AlbumPageUiState>
            = MutableStateFlow<AlbumPageUiState>(AlbumPageUiState.initial())

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
}