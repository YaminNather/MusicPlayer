package com.example.musicplayer.screens.albums.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.businesslogic.albumqueryapi.AlbumQueryApi
import com.example.businesslogic.apimodels.Album
import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.musicplayer.screens.PageRoutes.Companion.songs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsPageUiState
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class AlbumsPageViewModelImpl : AlbumsPageViewModel {
    internal constructor(
        albumQueryApi: AlbumQueryApi,
        songQueueApi: SongQueueApi,
    ) {
        this.albumQueryApi = albumQueryApi

        _state = MutableStateFlow<AlbumsPageUiState>(AlbumsPageUiState.initial())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    public override fun pageOpened(): Unit {
        coroutineScope.launch {
            val albums: List<Album> = albumQueryApi.getAll()

            val recentlyPlayedAlbums: List<Album> = albums

            _state.update { currentState ->
                return@update currentState.copy(
                    albums = albums.map { element ->
                        AlbumsListItemUiState(
                            id = element.id,
                            name = element.name,
                            artist = element.artist,
                            coverArt = element.coverArt,
                            moreMenuOpened = false,
                        )
                    },
                    recentlyPlayed = recentlyPlayedAlbums.map { element ->
                        AlbumsListItemUiState(
                            id = element.id,
                            name = element.name,
                            artist = element.artist,
                            coverArt = element.coverArt,
                            moreMenuOpened = false,
                        )
                    },
                )
            }
        }
    }

    public override fun albumListItemClicked(id: String) {
        _state.update { value -> value.copy(navigatingToAlbum = id) }
    }

    public override fun navigatedToAlbumPage() {
        _state.update { value -> value.copy(navigatingToAlbum = null) }
    }

    public override fun albumListItemMoreButtonClicked(id: String): Unit {
        _state.update { value ->
            value.copy(
                albums = value.albums.map { element ->
                    if (element.id == id) {
                        return@map element.copy(moreMenuOpened = true)
                    }
                    else {
                        if (element.moreMenuOpened) {
                            return@map element.copy(moreMenuOpened = false)
                        }
                        else {
                            return@map element
                        }
                    }
                }
            )
        }
    }

    public override fun albumListItemMoreMenuDismissed(id: String): Unit {
        _state.update { value ->
            value.copy(
                albums = value.albums.map { element ->
                    if (element.id != id) return@map element

                    return@map element.copy(moreMenuOpened = false)
                }
            )
        }
    }

    public override fun songListItemAddToQueueButtonClicked(id: String): Unit {

    }

    public override fun notificationDisplayed(): Unit {
        _state.update { value -> value.copy(isDisplayingNotification = true)}
    }

    public override fun notificationDismissed(): Unit {
        _state.update { value ->
            value.copy(
                isDisplayingNotification = false,
                pendingNotifications = value.pendingNotifications.let { notifications ->
                    if (notifications.size == 1) {
                        return@let listOf<String>()
                    }

                    return@let notifications.slice(IntRange(1, notifications.size - 1))
                }
            )
        }
    }

    public override fun toggleNavigationDrawerButtonClicked(): Unit {
        _state.update {
            value -> value.copy(navigationDrawerIsOpen = !value.navigationDrawerIsOpen)
        }
    }

    public override fun navigationDrawerDismissed(): Unit {
        _state.update { value -> value.copy(navigationDrawerIsOpen = false) }
    }

    public override val state: StateFlow<AlbumsPageUiState> get() = _state



    private val _state: MutableStateFlow<AlbumsPageUiState>

    private val albumQueryApi: AlbumQueryApi

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
}
