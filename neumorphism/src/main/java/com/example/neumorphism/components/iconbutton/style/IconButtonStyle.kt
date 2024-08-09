package com.example.neumorphism.components.iconbutton.style

import androidx.compose.ui.graphics.Color
import com.example.neumorphism.theme.NeumorphicTheme

public data class IconButtonStyle(
    public val idle: IconButtonStateStyle,
    public val pressed: IconButtonStateStyle,
) {
    public companion object {
        public fun resolve(
            cascadingStyle: CascadingIconButtonStyle?,
            theme: NeumorphicTheme,
        ): IconButtonStyle {
            return IconButtonStyle(
                idle = IconButtonStateStyle.resolveIdle(cascadingStyle?.idle, theme, theme.resolvedColorScheme().text),
                pressed = IconButtonStateStyle.resolvePressed(cascadingStyle?.pressed, theme, theme.resolvedColorScheme().text),
            )
        }
    }
}