package com.example.musicplayer.screens.songqueue.viewmodel

import com.example.musicplayer.screens.songqueue.viewmodel.uistate.SongQueueItemUiState
import com.example.musicplayer.screens.songqueue.viewmodel.uistate.SongQueuePageUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PreviewViewModel : SongQueuePageViewModel {
    public constructor() {
        _uiState = MutableStateFlow<SongQueuePageUiState>(
            SongQueuePageUiState(
                songQueueItems = List<SongQueueItemUiState>(5) { index ->
                    SongQueueItemUiState(
                        id = index,
                        name = "Song $index",
                        artist = "Random Artist",
                        isPlaying = index == 0,
                        isMoreOptionsMenuOpen = false,
                    )
                }
            )
        )
    }

    public override fun pageOpened(): Unit { }

    public override fun queueItemClicked(id: Int) { }

    override fun queueItemMoreOptionsButtonClicked(id: Int) { }

    override fun removeQueueItemButtonClicked(id: Int) {}

    override fun queueItemMoreOptionsMenuDismissed(id: Int) {}

    public override fun closed(): Unit {}

    public override val uiState: StateFlow<SongQueuePageUiState>
        get() = _uiState.asStateFlow()

    private val _uiState: MutableStateFlow<SongQueuePageUiState>
}