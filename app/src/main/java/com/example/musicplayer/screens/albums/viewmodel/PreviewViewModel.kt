package com.example.musicplayer.screens.albums.viewmodel

import android.net.Uri
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsPageUiState
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PreviewViewModel: AlbumsPageViewModel {
    override fun pageOpened() { }

    override fun albumListItemClicked(id: String) { }

    override fun albumListItemMoreButtonClicked(id: String) { }

    override fun albumListItemMoreMenuDismissed(id: String) { }

    override fun songListItemAddToQueueButtonClicked(id: String) { }

    override fun navigatedToAlbumPage() { }

    override fun notificationDisplayed() { }

    override fun notificationDismissed() { }

    override fun toggleNavigationDrawerButtonClicked() { }

    override fun navigationDrawerDismissed() { }


    public override val state: StateFlow<AlbumsPageUiState> get() = _state.asStateFlow()

    private val _state: MutableStateFlow<AlbumsPageUiState> = MutableStateFlow(
        AlbumsPageUiState(
            albums = List<AlbumsListItemUiState>(5) { index ->
                AlbumsListItemUiState(
                    id = "id_$index",
                    name = "Album $index",
                    artist = "Random Artist",
                    moreMenuOpened = false,
                    coverArt = Uri.parse("content://fjiefjeifeifeif"),
                )
            },
            recentlyPlayed =  List<AlbumsListItemUiState>(5) { index ->
                AlbumsListItemUiState(
                    id = "id_$index",
                    name = "Album $index",
                    artist = "Random Artist",
                    moreMenuOpened = false,
                    coverArt = Uri.parse("content://fjiefjeifeifeif"),
                )
            },
            navigatingToAlbum = null,
            pendingNotifications = listOf<String>(),
            isDisplayingNotification = false,
            navigationDrawerIsOpen = true,
        )
    )
}