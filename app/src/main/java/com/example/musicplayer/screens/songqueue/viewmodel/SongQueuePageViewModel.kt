package com.example.musicplayer.screens.songqueue.viewmodel

import com.example.musicplayer.screens.songqueue.viewmodel.uistate.SongQueuePageUiState
import kotlinx.coroutines.flow.StateFlow

internal interface SongQueuePageViewModel {
    public fun pageOpened(): Unit

    public fun queueItemClicked(id: Int): Unit

    public fun queueItemMoreOptionsButtonClicked(id: Int): Unit

    public fun removeQueueItemButtonClicked(id: Int): Unit

    public fun queueItemMoreOptionsMenuDismissed(id: Int): Unit

    public fun closed(): Unit

    public val uiState: StateFlow<SongQueuePageUiState>
}