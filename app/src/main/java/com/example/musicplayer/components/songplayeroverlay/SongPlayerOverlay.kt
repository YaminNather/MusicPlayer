package com.example.musicplayer.components.songplayeroverlay

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.IntOffset
import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.MinimizedSongPlayer
import com.example.musicplayer.components.songplayeroverlay.viewmodel.SongPlayerOverlayUiState
import com.example.musicplayer.components.songplayeroverlay.viewmodel.SongPlayerOverlayViewModel
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.maximizedsongplayer.MaximizedSongPlayerPage
import com.example.musicplayer.screens.songplayer.SongPlayerPage

@Composable
internal fun SongPlayerOverlay() {
    val songPlayerOverlayState: SongPlayerOverlayState = LocalSongPlayerOverlayState.current!!

    when (songPlayerOverlayState.songPlayerType) {
        null -> {
            return
        }
        SongPlayerType.Minimized -> {
            MinimizedSongPlayerOverlay()
        }
        else -> {
            MaximizedSongPlayerOverlay()
        }
    }
}

@Composable
private fun MaximizedSongPlayerOverlay() {
    SongPlayerPage()
}

@Composable
private fun MinimizedSongPlayerOverlay() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        MinimizedSongPlayer()
    }
}
