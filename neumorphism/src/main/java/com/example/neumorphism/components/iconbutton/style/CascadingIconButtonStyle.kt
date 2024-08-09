package com.example.neumorphism.components.iconbutton.style

public data class CascadingIconButtonStyle(
    public val idle: CascadingIconButtonStateStyle? = null,
    public val pressed: CascadingIconButtonStateStyle? = null,
) {
    public fun merge(mergeWith: CascadingIconButtonStyle?): CascadingIconButtonStyle {
        return CascadingIconButtonStyle(
            idle = idle?.merge(mergeWith?.idle) ?: mergeWith?.idle,
            pressed = pressed?.merge(mergeWith?.pressed) ?: mergeWith?.pressed,
        )
    }
}