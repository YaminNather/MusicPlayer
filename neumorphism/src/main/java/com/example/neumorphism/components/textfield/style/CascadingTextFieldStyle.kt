package com.example.neumorphism.components.textfield.style

import androidx.compose.ui.graphics.Color
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle

public data class CascadingTextFieldStyle(
    public val neumorphicStyle: CascadingNeumorphicStyle?,
    public val textColor: Color?,
) {
    public fun merge(with: CascadingTextFieldStyle?): CascadingTextFieldStyle {
        return CascadingTextFieldStyle(
            neumorphicStyle = with?.neumorphicStyle ?: neumorphicStyle,
            textColor = with?.textColor ?: textColor,
        )
    }
}