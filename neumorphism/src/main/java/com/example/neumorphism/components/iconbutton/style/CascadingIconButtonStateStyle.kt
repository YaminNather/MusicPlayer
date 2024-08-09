package com.example.neumorphism.components.iconbutton.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.neumorphism.components.button.style.CascadingButtonStateStyle
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle

public data class CascadingIconButtonStateStyle(
    public val neumorphicStyle: CascadingNeumorphicStyle? = null,
    public val iconColor: Color? = null,
    public val padding: Dp? = null,
) {
    public fun merge(mergeWith: CascadingIconButtonStateStyle?): CascadingIconButtonStateStyle {
        return CascadingIconButtonStateStyle(
            neumorphicStyle = neumorphicStyle?.merge(mergeWith?.neumorphicStyle) ?: mergeWith?.neumorphicStyle,
            iconColor = mergeWith?.iconColor ?: iconColor,
            padding = mergeWith?.padding ?: padding,
        )
    }
}