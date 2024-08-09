package com.example.musicplayer.screens.maximizedsongplayer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.SkipNext
import androidx.compose.material.icons.outlined.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.components.playpausebutton.PlayPauseButton
import com.example.musicplayer.screens.songplayer.viewmodel.SongPlayerPageViewModel
import com.example.musicplayer.screens.songplayer.viewmodel.uistate.SongPlayerPageUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.components.iconbutton.style.CascadingIconButtonStateStyle
import com.example.neumorphism.components.iconbutton.style.CascadingIconButtonStyle
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.theme.ThemeProvider

@Composable
internal fun MusicControls(viewModel: SongPlayerPageViewModel) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    val uiState: SongPlayerPageUiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .neumorphic(shape = RoundedCornerShape(3000.dp))
    ) {
        Column(
            modifier = Modifier
                .neumorphic(
                    shape = RoundedCornerShape(3000.dp),
                    height = -theme.resolvedHeight() * 4f,
                )
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ThemeProvider(
                theme = NeumorphicTheme(
                    iconButton = CascadingIconButtonStyle(
                        idle = CascadingIconButtonStateStyle(
                            neumorphicStyle = CascadingNeumorphicStyle(height = 0.dp)
                        ),
                        pressed = CascadingIconButtonStateStyle(
                            neumorphicStyle = CascadingNeumorphicStyle(
                                height = -LocalNeumorphicTheme.current!!.resolvedHeight()
                            )
                        )
                    )
                )
            ) {
                CompositionLocalProvider(LocalContentColor.provides(Color(0xFF7FB2D5))) {
                    IconButton(onClick = { viewModel.restartSongButtonClicked() }) {
                        Icon(Icons.Outlined.Refresh, "Restart")
                    }

                    IconButton(onClick = { viewModel.previousSongButtonClicked() }) {
                        Icon(Icons.Outlined.SkipPrevious, "Previous")
                    }

                    PlayPauseButton(isPlaying = !uiState.isPaused) {
                        viewModel.playButtonClicked()
                    }

                    IconButton(onClick = { viewModel.nextSongButtonClicked() }) {
                        Icon(Icons.Outlined.SkipNext, "Next")
                    }

                    IconButton({}) { Icon(Icons.Filled.Favorite, "Favourite", tint = Color.Red) }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MusicControlsPreview() {
    val viewModel: PreviewViewModel = remember { PreviewViewModel() }

    RootThemeProvider {
        Scaffold() { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                MusicControls(viewModel = viewModel)
            }
        }
    }
}