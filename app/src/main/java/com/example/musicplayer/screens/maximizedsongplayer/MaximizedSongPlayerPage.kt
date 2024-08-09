package com.example.musicplayer.screens.maximizedsongplayer

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp

import androidx.compose.ui.Modifier

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.songplayeroverlay.LocalSongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerType
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.songplayer.viewmodel.SongPlayerPageViewModel
import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState
import com.example.musicplayer.ui.theme.RootThemeProvider

import com.example.neumorphism.components.iconbutton.IconButton as NeumorphicIconButton

val pagePadding: Dp = 16.dp

@Composable
internal fun MaximizedSongPlayerPage(viewModel: SongPlayerPageViewModel? = null) {
    val finalViewModel: SongPlayerPageViewModel = remember {
        viewModel ?: koinContainer.getValue<SongPlayerPageViewModel>()
    }

    val uiState: SongPlayerPageUiState by finalViewModel.uiState.collectAsState()

    val songPlayerOverlayState: SongPlayerOverlayState = LocalSongPlayerOverlayState.current!!

    DisposableEffect(Unit) {
        finalViewModel.pageOpened()

        onDispose { finalViewModel.pageClosed() }
    }

    CustomScaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 32.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .padding(horizontal = pagePadding),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    NeumorphicIconButton(
                        onClick = {
                            songPlayerOverlayState.setOpenSongPlayerType(SongPlayerType.Minimized)
                        }
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, null)
                    }

                    Spacer(Modifier.width(16.dp))

                    Column(modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            text = uiState.songName,
                            style = MaterialTheme.typography.headlineMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            text = uiState.artistName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = pagePadding),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MusicControls(viewModel = finalViewModel)

                    Box(
                        modifier = Modifier
                            .padding(start = 32.dp)
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        RecordPlayer(uiState.songProgressFactor, coverArt = uiState.coverArt)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Lyrics()

                    ExpandLyricsIndicator()
                }
            }
        }
    )
}

@Preview(device = "spec:width=1080px,height=2246px,dpi=480")
//@Preview(device = "id:Nexus 5")
@Composable
private fun Preview() {
    val songPlayerOverlayState: SongPlayerOverlayState = remember {
        SongPlayerOverlayState(SongPlayerType.Maximized) {}
    }

    RootThemeProvider {
        CompositionLocalProvider(LocalSongPlayerOverlayState.provides(songPlayerOverlayState)) {
            MaximizedSongPlayerPage(viewModel = PreviewViewModel())
        }
    }
}