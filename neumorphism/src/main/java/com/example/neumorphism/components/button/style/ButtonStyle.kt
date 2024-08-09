package com.example.neumorphism.components.button.style

import com.example.neumorphism.theme.NeumorphicTheme

public data class ButtonStyle(
    public val idle: ButtonStateStyle,
    public val focused: ButtonStateStyle,
    public val pressed: ButtonStateStyle,
) {
    public companion object {
        public fun resolve(cascadingStyle: CascadingButtonStyle?, theme: NeumorphicTheme): ButtonStyle {
            return ButtonStyle(
                idle = ButtonStateStyle.resolveToIdle(cascadingStyle?.idle, theme),
                focused = ButtonStateStyle.resolveToFocused(cascadingStyle?.focused, theme),
                pressed = ButtonStateStyle.resolveToPressed(cascadingStyle?.pressed, theme),
            )
        }
    }
}