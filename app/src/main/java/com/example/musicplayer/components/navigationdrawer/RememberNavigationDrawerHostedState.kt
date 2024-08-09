package com.example.musicplayer.components.navigationdrawer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
internal fun rememberNavigationDrawerHostedState(): NavigationDrawerHostedState {
    var isOpen by remember { mutableStateOf<Boolean>(false) }

    val contentOffsetFactor by animateFloatAsState(
        if (!isOpen) 0f else 1f,
        animationSpec = tween(500),
        label = "Content Offset"
    )

    return NavigationDrawerHostedState(
        contentOffsetFactor = contentOffsetFactor,
        isOpen = isOpen,
        open =  { isOpen = true },
        close = { isOpen = false },
    )
}