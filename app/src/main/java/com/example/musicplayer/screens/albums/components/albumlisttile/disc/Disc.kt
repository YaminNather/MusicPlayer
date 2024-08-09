package com.example.musicplayer.screens.albums.components.albumlisttile.disc

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.theme.LocalNeumorphicTheme

@Composable
internal fun Disc(modifier: Modifier = Modifier, isSpinning: Boolean = false, coverArt: @Composable () -> Unit) {
    val rotationAnimatable = remember { Animatable(0f) }

    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            rotationAnimatable.snapTo(0f)
            rotationAnimatable.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(5000, easing = LinearEasing)),
            )
        }
        else {
            rotationAnimatable.snapTo(0f)
            rotationAnimatable.stop()
        }
    }

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(3000.dp))
//            .rotate(rotationAnimatable.value)
    ) {
        coverArt()
    }
}

@Preview
@Composable
private fun DiscPreview() {
    RootThemeProvider {
        Box(
            modifier = Modifier
                .background(LocalNeumorphicTheme.current!!.resolvedColorScheme().background)
                .padding(16.dp),
        ) {
            Disc {
                Image(
                    painterResource(R.drawable.songcover),
                    null,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
    }
}