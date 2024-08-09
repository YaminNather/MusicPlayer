package com.example.musicplayer.screens.selectsong.viewmodel

import com.example.businesslogic.apimodels.Song
import com.example.businesslogic.songplayersystem.songqueueapi.SongQueueApi
import com.example.businesslogic.songqueryapi.SongQueryApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsPageUiState
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SelectSongPageUiState
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SongListItemUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public class SelectSongPageViewModelImpl : SelectSongPageViewModel {
    internal constructor(
        songQueryApi: SongQueryApi,
        songQueueApi: SongQueueApi,
    ) {
        this.songQueryApi = songQueryApi
        this.songQueueApi = songQueueApi

        _state = MutableStateFlow<SelectSongPageUiState>(SelectSongPageUiState.initial())
    }

    public override fun pageOpened(): Unit {
        coroutineScope.launch {
            val songs: List<Song> = songQueryApi.getAll()
            val currentSong: Song? = songQueueApi.getCurrentSong()

            val recentlyPlayedSongs: List<Song> = songQueryApi.getRecentlyPlayed()

            songQueueApi.addCurrentSongUpdatedListener(
                this@SelectSongPageViewModelImpl::currentSongUpdatedListener
            )

            _state.update { currentState ->
                return@update currentState.copy(
                    songs = songs.map { element ->
                        SongListItemUiState(
                            id = element.id,
                            name = element.name,
                            artist = element.artist,
                            coverArt = element.coverArt,
                            isPlaying = element.id == currentSong?.id,
                            moreMenuOpened = false,
                        )
                    },
                    recentlyPlayed = recentlyPlayedSongs.map { element ->
                        SongListItemUiState(
                            id = element.id,
                            name = element.name,
                            artist = element.artist,
                            coverArt = element.coverArt,
                            isPlaying = element.id == currentSong?.id,
                            moreMenuOpened = false,
                        )
                    },
                )
            }
        }
    }

    public override fun songListItemClicked(id: String) {
        songQueueApi.clear()
        songQueueApi.addAsNext(id)

        _state.update { currentState ->
            currentState.copy(navigateToSongPage = true)
        }
    }

    public override fun navigatedToSongPlayerPage() {
        _state.update { currentState ->
            currentState.copy(navigateToSongPage = false)
        }
    }

    public override fun songListItemMoreButtonClicked(id: String): Unit {
        _state.update { value ->
            value.copy(
                songs = value.songs.map { element ->
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

    public override fun songListItemMoreMenuDismissed(id: String): Unit {
        _state.update { value ->
            value.copy(
                songs = value.songs.map { element ->
                    if (element.id != id) return@map element

                    return@map element.copy(moreMenuOpened = false)
                }
            )
        }
    }

    public override fun songListItemAddToQueueButtonClicked(id: String): Unit {
        songQueueApi.addToEnd(id)

        _state.update { value ->
            value.copy(
                songs = value.songs
                    .map { element ->
                        if (element.moreMenuOpened) element.copy(moreMenuOpened = false)
                        else element
                    },
                pendingNotifications = value.pendingNotifications + listOf("Added to queue"),
            )
        }
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

    public override fun pageClosed(): Unit {
        songQueueApi.removeCurrentSongUpdatedListener(this::currentSongUpdatedListener)
    }

    private fun currentSongUpdatedListener(song: Song?): Unit {
        _state.update { value ->
            value.copy(
                songs = value.songs.map { element ->
                    element.copy(isPlaying = element.id == song?.id)
                }
            )
        }
    }

    public override val state: StateFlow<SelectSongPageUiState> get() = _state



    private val _state: MutableStateFlow<SelectSongPageUiState>

    private val songQueryApi: SongQueryApi
    private val songQueueApi: SongQueueApi

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
}
