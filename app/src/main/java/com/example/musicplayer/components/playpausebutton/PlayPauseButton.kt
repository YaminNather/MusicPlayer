package com.example.musicplayer.components.playpausebutton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.components.iconbutton.style.CascadingIconButtonStateStyle
import com.example.neumorphism.components.iconbutton.style.CascadingIconButtonStyle
import com.example.neumorphism.components.iconbutton.style.IconButtonStyle
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
public fun PlayPauseButton(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val style = calculateStyle(isPlaying)

    IconButton(modifier = modifier, onClick = onClick, style = style) {
        Icon(
            modifier = iconModifier,
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = null,
        )
    }
}

@Composable
private fun calculateStyle(isPlaying: Boolean): CascadingIconButtonStyle? {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!
    val buttonStyle: IconButtonStyle = theme.resolvedIconButtonStyle()

    val style: CascadingIconButtonStyle? = remember<CascadingIconButtonStyle?>(isPlaying) {
        if (isPlaying) {
            val stateStyle: CascadingIconButtonStateStyle = CascadingIconButtonStateStyle(
                neumorphicStyle = CascadingNeumorphicStyle(
                    highlightColor = buttonStyle.pressed.neumorphicStyle.highlightColor.copy(0.4f),
                    shadowColor = buttonStyle.pressed.neumorphicStyle.shadowColor.copy(0.3f),
                    height = buttonStyle.pressed.neumorphicStyle.height,
                    backgroundColor = theme.resolvedColorScheme().primary,
                ),
                iconColor = Color.White,
            )
            return@remember CascadingIconButtonStyle(stateStyle, stateStyle)
        }
        else {
            return@remember null
        }
    }

    return style
}

@Preview
@Composable
private fun PlayPauseButtonPreview() {
    RootThemeProvider {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(LocalNeumorphicTheme.current!!.resolvedColorScheme().background)
                .padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                PlayPauseButton(false) {}

                Spacer(modifier = Modifier.height(32.dp))

                PlayPauseButton(true) {}
            }
        }
    }
}
