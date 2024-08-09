package com.example.neumorphism.components.button.style

public data class CascadingButtonStyle(
    public val idle: CascadingButtonStateStyle? = null,
    public val focused: CascadingButtonStateStyle? = null,
    public val pressed: CascadingButtonStateStyle? = null,
) {
    public fun merge(mergeWith: CascadingButtonStyle?): CascadingButtonStyle {
        if (mergeWith == null) return this

        return CascadingButtonStyle(
            idle = idle?.merge(mergeWith.idle) ?: mergeWith.idle,
            focused = focused?.merge(mergeWith.focused) ?: mergeWith.focused,
            pressed = pressed?.merge(mergeWith.pressed) ?: mergeWith.pressed,
        )
    }
}