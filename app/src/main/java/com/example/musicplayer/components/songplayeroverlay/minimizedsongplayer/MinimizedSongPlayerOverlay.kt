package com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.RootThemeProvider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.MinimizedSongPlayerOverlayViewModel
import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.PreviewViewModel
import com.example.musicplayer.components.songplayeroverlay.minimizedsongplayer.viewmodel.uiState.MinimizedSongPlayerOverlayUiState
import com.example.musicplayer.components.playpausebutton.PlayPauseButton
import com.example.musicplayer.components.songplayeroverlay.LocalSongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerType
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MinimizedSongPlayer(
    viewModel: MinimizedSongPlayerOverlayViewModel? = null,
) {
    val finalViewModel = remember<MinimizedSongPlayerOverlayViewModel> {
        viewModel ?: koinContainer.getValue<MinimizedSongPlayerOverlayViewModel>()
    }

    val uiState: MinimizedSongPlayerOverlayUiState by finalViewModel.uiState.collectAsState()

    val songPlayerOverlayState: SongPlayerOverlayState = LocalSongPlayerOverlayState.current!!

    DisposableEffect(Unit) {
        finalViewModel.opened()

        onDispose { finalViewModel.closed() }
    }

    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                songPlayerOverlayState.setOpenSongPlayerType(SongPlayerType.Maximized)
            }
            .neumorphic(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 4.dp, vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(3000.dp))
                        .neumorphic(height = (-4).dp, shape = RoundedCornerShape(3000.dp))
                        .padding(2.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .neumorphic(shape = RoundedCornerShape(3000.dp))
                            .padding(6.dp)
                    ) {
                        PlayPauseButton(isPlaying = uiState.isPlaying) {
                            finalViewModel.pauseButtonClicked()
                        }
                    }

                }

                CircularProgressIndicator(
                    modifier = Modifier.size(56.dp),
                    progress = uiState.progress,
                    strokeWidth = 2.dp,
                    color = theme.resolvedColorScheme().primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    uiState.songName,
                    modifier = Modifier.fillMaxWidth().basicMarquee(Int.MAX_VALUE),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    uiState.artist,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = { finalViewModel.toPreviousSongButtonClicked() }) {
                Icon(Icons.Default.SkipPrevious, null)
            }

            Spacer(Modifier.width(16.dp))

            IconButton(onClick = { finalViewModel.toNextSongButtonClicked()}) {
                Icon(Icons.Default.SkipNext, null)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = "id:Nexus 5")
@Composable
private fun MinimizedSongPlayerOverlayPreview() {
    val viewModel: MinimizedSongPlayerOverlayViewModel = PreviewViewModel()

    val songPlayerOverlayState: SongPlayerOverlayState = SongPlayerOverlayState(
        SongPlayerType.Minimized,
    ) {}

    RootThemeProvider {
        CompositionLocalProvider(LocalSongPlayerOverlayState.provides(songPlayerOverlayState)){
            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    Spacer(modifier = Modifier.padding(padding))
                }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    MinimizedSongPlayer(viewModel)
                }
            }
        }
    }
}