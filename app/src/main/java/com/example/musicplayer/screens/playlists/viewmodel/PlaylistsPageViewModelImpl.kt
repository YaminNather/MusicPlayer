package com.example.musicplayer.screens.playlists.viewmodel

import androidx.annotation.IntRange
import com.example.businesslogic.apimodels.Playlist
import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistListItemUiState
import com.example.musicplayer.screens.playlists.viewmodel.uistate.PlaylistsPageUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class PlaylistsPageViewModelImpl(
    private val playlistApi: PlaylistApi,
) : PlaylistsPageViewModel {
    override fun pageOpened() {
        coroutineScope.launch {
            val playlists: List<Playlist> = playlistApi.getAll()

            _uiState.update { value ->
                value.copy(
                    playlistListItems = playlists
                        .map(this@PlaylistsPageViewModelImpl::mapPlaylistItemToUiState)
                )
            }
        }
    }

    override fun createPlaylistButtonClicked() {
        _uiState.update { value -> value.copy(isCreatePlaylistDialogOpen = true) }
    }

    override fun createPlaylistDialogClosed(result: Boolean) {
        _uiState.update { value -> value.copy(isCreatePlaylistDialogOpen = false) }

        if (!result) return

        _uiState.update { value ->
            value.copy(pendingNotifications = value.pendingNotifications + "Playlist created")
        }

        coroutineScope.launch {
            _uiState.update { value ->
                value.copy(
                    playlistListItems = playlistApi.getAll()
                        .map(this@PlaylistsPageViewModelImpl::mapPlaylistItemToUiState),
                )
            }
        }
    }

    override fun playlistListItemClicked(id: String) {
        _uiState.update { value -> value.copy(navigatingToPlaylistPage = id) }
    }

    override fun notificationShown() {
        _uiState.update { value ->
            value.copy(
                pendingNotifications = value.pendingNotifications
                    .slice(IntRange(1, value.pendingNotifications.size - 1))
            )
        }
    }

    override fun navigatedToPlaylistPage() {
        _uiState.update { value -> value.copy(navigatingToPlaylistPage = null) }
    }

    override fun navigationDrawerButtonClicked() {
        _uiState.update { value ->
            value.copy(isNavigationDrawerOpen = !value.isNavigationDrawerOpen)
        }
    }

    override val uiState: StateFlow<PlaylistsPageUiState> get() = _uiState.asStateFlow()

    private fun mapPlaylistItemToUiState(element: Playlist): PlaylistListItemUiState {
        return PlaylistListItemUiState(
            id = element.id,
            name = element.name,
            coverArt = null,
            songCount = element.songs.size,
        )
    }




    private val _uiState: MutableStateFlow<PlaylistsPageUiState>
        = MutableStateFlow(PlaylistsPageUiState.initial())

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate)
}