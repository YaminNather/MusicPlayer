package com.example.musicplayer.components.songplayeroverlay

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

internal data class SongPlayerOverlayState(
    public val songPlayerType: SongPlayerType?,
    public val setOpenSongPlayerType: (value: SongPlayerType) -> Unit,
)

internal val LocalSongPlayerOverlayState: ProvidableCompositionLocal<SongPlayerOverlayState?> =
    compositionLocalOf<SongPlayerOverlayState?> { null }
