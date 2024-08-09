package com.example.musicplayer.screens.selectsong.viewmodel

import kotlinx.coroutines.flow.StateFlow
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsPageUiState
import com.example.musicplayer.screens.selectsong.viewmodel.uistate.SelectSongPageUiState

public interface SelectSongPageViewModel {
    public fun pageOpened(): Unit

    public fun songListItemClicked(id: String): Unit

    public fun songListItemMoreButtonClicked(id: String): Unit

    public fun songListItemMoreMenuDismissed(id: String): Unit

    public fun songListItemAddToQueueButtonClicked(id: String): Unit

    public fun navigatedToSongPlayerPage(): Unit

    public fun notificationDisplayed(): Unit

    public fun notificationDismissed(): Unit

    public fun toggleNavigationDrawerButtonClicked(): Unit

    public fun navigationDrawerDismissed(): Unit

    public fun pageClosed(): Unit

    public val state: StateFlow<SelectSongPageUiState>
}