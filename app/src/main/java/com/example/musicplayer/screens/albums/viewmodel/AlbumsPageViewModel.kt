package com.example.musicplayer.screens.albums.viewmodel

import kotlinx.coroutines.flow.StateFlow
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsPageUiState

internal interface AlbumsPageViewModel {
    public fun pageOpened(): Unit

    public fun albumListItemClicked(id: String): Unit

    public fun albumListItemMoreButtonClicked(id: String): Unit

    public fun albumListItemMoreMenuDismissed(id: String): Unit

    public fun songListItemAddToQueueButtonClicked(id: String): Unit

    public fun navigatedToAlbumPage(): Unit

    public fun notificationDisplayed(): Unit

    public fun notificationDismissed(): Unit

    public fun toggleNavigationDrawerButtonClicked(): Unit

    public fun navigationDrawerDismissed(): Unit

    public val state: StateFlow<AlbumsPageUiState>
}