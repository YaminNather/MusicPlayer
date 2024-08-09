package com.example.musicplayer.components.navigationdrawer

internal data class NavigationDrawerHostedState(
    public val contentOffsetFactor: Float,
    public val isOpen: Boolean,
    public val open: () -> Unit,
    public val close: () -> Unit,
)