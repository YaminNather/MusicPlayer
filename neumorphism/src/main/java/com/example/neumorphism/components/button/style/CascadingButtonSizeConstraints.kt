package com.example.neumorphism.components.button.style

import androidx.compose.ui.unit.Dp

public data class CascadingButtonSizeConstraints(
    public val minWidth: Dp?,
    public val minHeight: Dp?,
    public val maxWidth: Dp?,
    public val maxHeight: Dp?,
) {
    public fun merge(mergeWith:CascadingButtonSizeConstraints?): CascadingButtonSizeConstraints {
        if (mergeWith == null) return this

        return CascadingButtonSizeConstraints(
            minWidth = mergeWith.minWidth ?: minWidth,
            minHeight = mergeWith.minHeight ?: minHeight,
            maxWidth = mergeWith.maxWidth ?: maxWidth,
            maxHeight = mergeWith.maxHeight ?: maxHeight,
        )
    }
}