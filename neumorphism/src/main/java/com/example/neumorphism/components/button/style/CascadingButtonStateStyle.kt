package com.example.neumorphism.components.button.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle

public data class CascadingButtonStateStyle(
    public val neumorphicStyle: CascadingNeumorphicStyle? = null,
    public val constraints: CascadingButtonSizeConstraints? = null,
    public val padding: PaddingValues? = null,
    public val backgroundColor: Color? = null,
    public val foregroundColor: Color? = null,
)  {
    public fun merge(mergeWith: CascadingButtonStateStyle?): CascadingButtonStateStyle {
        if (mergeWith == null) return this

        return CascadingButtonStateStyle(
            neumorphicStyle = neumorphicStyle?.merge(mergeWith.neumorphicStyle) ?: mergeWith.neumorphicStyle,
            constraints = constraints?.merge(mergeWith.constraints) ?: mergeWith.constraints,
            padding = mergeWith.padding ?: padding,
            backgroundColor = mergeWith.backgroundColor ?: backgroundColor,
            foregroundColor = mergeWith.foregroundColor ?: foregroundColor,
        )
    }
}