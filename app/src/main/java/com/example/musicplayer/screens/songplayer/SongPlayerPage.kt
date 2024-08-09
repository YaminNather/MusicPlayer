package com.example.musicplayer.screens.songplayer

import android.net.Uri
import android.text.format.DateUtils
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.components.playpausebutton.PlayPauseButton
import com.example.musicplayer.components.songplayeroverlay.LocalSongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerOverlayState
import com.example.musicplayer.components.songplayeroverlay.SongPlayerType
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.songplayer.components.ProgressBar
import com.example.musicplayer.screens.songplayer.viewmodel.SongPlayerPageViewModel
import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme
import kotlin.time.DurationUnit

@Composable
internal fun SongPlayerPage(viewModel: SongPlayerPageViewModel? = null) {
    val internalViewModel = remember<SongPlayerPageViewModel> {
        viewModel ?: koinContainer.getValue<SongPlayerPageViewModel>()
    }

    val uiState by internalViewModel.uiState.collectAsState()

    val songPlayerOverlayState: SongPlayerOverlayState = LocalSongPlayerOverlayState.current!!

    var progress by remember { mutableFloatStateOf(0f) }

    DisposableEffect(Unit) {
        internalViewModel.pageOpened()

        onDispose { internalViewModel.pageClosed() }
    }

    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    CustomScaffold(
        topBar = {
            CustomTopAppBar(
                navigationButton = {
                    IconButton(
                        onClick = {
                            songPlayerOverlayState.setOpenSongPlayerType(SongPlayerType.Minimized)
                        }
                    ) {
                        Icon(Icons.Default.KeyboardArrowDown, null)
                    }
                },
                title = { Text("PLAYING NOW") },
                actions = {
                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CoverArt(uiState.coverArt)

            Spacer(Modifier.height(16.dp))

            SongDetailsWithIcons(name = uiState.songName, artist = uiState.artistName)

            Spacer(Modifier.weight(1f))

//            Slider(
//                value = uiState.songProgress,
//                onValueChange = { value -> progress = value },
//                onValueChangeFinished = { internalViewModel.progressSeekFinished(progress) },
//                colors = SliderDefaults.colors(
//                    inactiveTrackColor = theme.resolvedColorScheme().background,
//                ),
//            )


            Column(modifier = Modifier.fillMaxWidth()) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(DateUtils.formatElapsedTime(uiState.songProgress.inWholeSeconds))
//
//                    Text(DateUtils.formatElapsedTime(uiState.songDuration.inWholeSeconds))
//                }

                ProgressBar(value = uiState.songProgressFactor) { value ->
                    internalViewModel.progressSeeked(value)
                }
            }

            Spacer(Modifier.weight(1f))

            LargeScreenBottomControls(viewModel = internalViewModel, uiState = uiState)
        }
    }
}

@Composable
private fun CoverArt(uri: Uri?) {
    val screenHeight: Dp = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .size(if (screenHeight < largeBreakpoint) 200.dp else 256.dp)
            .aspectRatio(1f)
            .neumorphic(height = 16.dp, shape = RoundedCornerShape(3000.dp))
            .padding(8.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(3000.dp))
                .fillMaxSize(),
            model = uri,
            placeholder = painterResource(R.drawable.songcover),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SongDetailsWithIcons(name: String, artist: String) {
    val screenHeight: Dp = LocalConfiguration.current.screenHeightDp.dp

    Row(verticalAlignment = Alignment.CenterVertically) {
        if (screenHeight < largeBreakpoint) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Shuffle, null)
            }
        }

        Spacer(Modifier.width(16.dp))

        Column(
            modifier = Modifier.let { modifier ->
                return@let if (screenHeight < largeBreakpoint) modifier.width(180.dp)
                else modifier
            },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                name,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(Int.MAX_VALUE),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )

            Text(
                artist,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
        }

        Spacer(Modifier.width(16.dp))

        if (screenHeight < largeBreakpoint) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Favorite, null)
            }
        }
    }
}

@Composable
private fun LargeScreenBottomControls(viewModel: SongPlayerPageViewModel, uiState: SongPlayerPageUiState) {
    val screenHeight: Dp = LocalConfiguration.current.screenHeightDp.dp

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (screenHeight > largeBreakpoint) {
            Row(horizontalArrangement = Arrangement.spacedBy(56.dp)) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Shuffle, null)
                }

                IconButton(onClick = {}) {
                    Icon(Icons.Default.Favorite, null)
                }
            }

            Spacer(Modifier.height(32.dp))
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val iconSize: Dp = 16.dp

            IconButton(onClick = { viewModel.previousSongButtonClicked() }) {
                Icon(
                    modifier = Modifier.padding(iconSize),
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = null,
                )
            }

            PlayPauseButton(iconModifier = Modifier.padding(iconSize), isPlaying = !uiState.isPaused) {
                viewModel.playButtonClicked()
            }

            IconButton(onClick = { viewModel.nextSongButtonClicked() }) {
                Icon(
                    modifier = Modifier.padding(iconSize),
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = null
                )
            }
        }
    }
}

val largeBreakpoint: Dp = 700.dp

//@Preview()
@Preview(device = "spec:width=1080px,height=2246px,dpi=480", showSystemUi = true)
//@Preview(device = "id:Nexus 5", showSystemUi = true)
@Composable
private fun SongPlayerPagePreview() {
    val songPlayerOverlayState: SongPlayerOverlayState = remember {
        SongPlayerOverlayState(SongPlayerType.Maximized) {}
    }

    val previewViewModel = remember<PreviewViewModel> { PreviewViewModel() }

    RootThemeProvider {
        CompositionLocalProvider(LocalSongPlayerOverlayState.provides(songPlayerOverlayState)) {
            SongPlayerPage(viewModel = previewViewModel)
        }
    }
}