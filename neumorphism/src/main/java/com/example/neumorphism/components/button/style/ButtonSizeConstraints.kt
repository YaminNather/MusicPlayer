package com.example.neumorphism.components.button.style

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public data class ButtonSizeConstraints(
    public val minWidth: Dp,
    public val minHeight: Dp,
    public val maxWidth: Dp,
    public val maxHeight: Dp,
) {
    public companion object {
        public fun resolve(cascadingStyle: CascadingButtonSizeConstraints?): ButtonSizeConstraints {
            return ButtonSizeConstraints(
                minWidth = cascadingStyle?.minWidth ?: 64.dp,
                minHeight = cascadingStyle?.minHeight ?: 0.dp,
                maxWidth = cascadingStyle?.maxWidth ?: 0.dp,
                maxHeight = cascadingStyle?.maxHeight ?: 0.dp,
            )
        }
    }
}