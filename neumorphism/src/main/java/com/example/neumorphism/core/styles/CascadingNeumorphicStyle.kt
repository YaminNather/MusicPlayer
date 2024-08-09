package com.example.neumorphism.core.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.example.neumorphism.components.button.style.ButtonSizeConstraints
import com.example.neumorphism.components.button.style.CascadingButtonSizeConstraints

public data class CascadingNeumorphicStyle(
    public val shape: Shape? = null,
    public val height: Dp? = null,
    public val backgroundColor: Color? = null,
    public val shadowColor: Color? = null,
    public val highlightColor: Color? = null,
    public val intensity: Float? = null,
) {
    public fun merge(mergeWith: CascadingNeumorphicStyle?): CascadingNeumorphicStyle {
        if (mergeWith == null) return this

        return CascadingNeumorphicStyle(
            shape = mergeWith.shape ?: shape,
            height = mergeWith.height ?: height,
            backgroundColor = mergeWith.backgroundColor ?: backgroundColor,
            shadowColor = mergeWith.shadowColor ?: shadowColor,
            highlightColor = mergeWith.highlightColor ?: highlightColor,
            intensity = mergeWith.intensity ?: intensity,
        )
    }
}