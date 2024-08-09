package com.example.musicplayer.screens.selectsong.viewmodel

import android.net.Uri
import com.example.musicplayer.screens.albums.viewmodel.AlbumsPageViewModel
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsPageUiState
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SelectSongPageUiState
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SongListItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PreviewViewModel: SelectSongPageViewModel {
    override fun pageOpened() { }

    override fun songListItemClicked(id: String) { }

    override fun songListItemMoreButtonClicked(id: String) { }

    override fun songListItemMoreMenuDismissed(id: String) { }

    override fun songListItemAddToQueueButtonClicked(id: String) { }

    override fun navigatedToSongPlayerPage() { }

    override fun notificationDisplayed() { }

    override fun notificationDismissed() { }

    override fun toggleNavigationDrawerButtonClicked() { }

    override fun navigationDrawerDismissed() { }

    public override fun pageClosed(): Unit { }

    public override val state: StateFlow<SelectSongPageUiState> get() = _state.asStateFlow()

    private val _state: MutableStateFlow<SelectSongPageUiState> = MutableStateFlow(
        SelectSongPageUiState(
            songs = List<SongListItemUiState>(5) { index ->
                SongListItemUiState(
                    id = "id_$index",
                    name = "Song $index",
                    artist = "Random Artist",
                    isPlaying = index == 2,
                    moreMenuOpened = false,
                    coverArt = Uri.parse("content://fjiefjeifeifeif"),
                )
            },
            recentlyPlayed =  List<SongListItemUiState>(5) { index ->
                SongListItemUiState(
                    id = "id_$index",
                    name = "Song $index",
                    artist = "Random Artist",
                    isPlaying = index == 2,
                    moreMenuOpened = false,
                    coverArt = Uri.parse("content://fjiefjeifeifeif"),
                )
            },
            navigateToSongPage = false,
            pendingNotifications = listOf<String>(),
            isDisplayingNotification = false,
            navigationDrawerIsOpen = true,
        )
    )
}