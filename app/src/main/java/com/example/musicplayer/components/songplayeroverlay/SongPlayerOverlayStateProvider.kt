package com.example.musicplayer.components.songplayeroverlay

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.musicplayer.components.songplayeroverlay.viewmodel.SongPlayerOverlayUiState
import com.example.musicplayer.components.songplayeroverlay.viewmodel.SongPlayerOverlayViewModel
import com.example.musicplayer.dependencyinjection.koinContainer

@Composable
public fun SongPlayerOverlayStateProvider(content: @Composable () -> Unit) {
    val viewModel = remember { koinContainer.getValue<SongPlayerOverlayViewModel>() }
    val uiState: SongPlayerOverlayUiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        viewModel.opened()

        onDispose { viewModel.closed() }
    }

    val songPlayerOverlayState: SongPlayerOverlayState = SongPlayerOverlayState(
        songPlayerType = uiState.songPlayerType,
        setOpenSongPlayerType = { value -> viewModel.songPlayerTypeChanged(value) }
    )

    CompositionLocalProvider(LocalSongPlayerOverlayState provides songPlayerOverlayState) {
        content()
    }
}