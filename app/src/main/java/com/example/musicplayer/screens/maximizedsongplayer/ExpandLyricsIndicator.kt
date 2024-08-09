package com.example.musicplayer.screens.maximizedsongplayer

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ExpandLyricsIndicator() {
    val scalingAnimationTransition = rememberInfiniteTransition("Scaling Animation")
    val scale by scalingAnimationTransition.animateFloat(
        label = "Scaling Animation",
        initialValue = 1f, targetValue = 1.1f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
    )

    Icon(
        Icons.Outlined.KeyboardArrowDown,
        "Open Lyrics",
        modifier = Modifier.graphicsLayer {
            this.scaleX = scale
            this.scaleY = scale
            this.transformOrigin = TransformOrigin.Center
        }
    )
}
