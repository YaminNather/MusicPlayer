package com.example.neumorphism.components.textfield.style

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.core.styles.NeumorphicStyle
import com.example.neumorphism.theme.NeumorphicTheme

public data class TextFieldStyle(
    public val neumorphicStyle: NeumorphicStyle,
    public val textColor: Color,
) {
    public companion object {
        public fun resolve(from: CascadingTextFieldStyle?, theme: NeumorphicTheme): TextFieldStyle {
            return TextFieldStyle(
                neumorphicStyle = resolveNeumorphicStyle(from?.neumorphicStyle, theme),
                textColor = from?.textColor ?: theme.resolvedColorScheme().text,
            )
        }

        private fun resolveNeumorphicStyle(
            from: CascadingNeumorphicStyle?,
            theme: NeumorphicTheme
        ): NeumorphicStyle {
            return NeumorphicStyle(
                height = from?.height ?: -theme.resolvedHeight(),
                intensity = from?.intensity ?: theme.resolvedIntensity(),
                shape = from?.shape ?: RoundedCornerShape(3000.dp),
                backgroundColor = from?.backgroundColor ?: theme.resolvedColorScheme().background,
                shadowColor = from?.shadowColor ?: theme.resolvedColorScheme().shadow,
                highlightColor = from?.highlightColor ?: theme.resolvedColorScheme().highlight,
            )
        }
    }
}