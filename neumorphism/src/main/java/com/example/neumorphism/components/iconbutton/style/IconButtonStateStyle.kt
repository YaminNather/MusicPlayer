package com.example.neumorphism.components.iconbutton.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.button.style.CascadingButtonStateStyle
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.core.styles.NeumorphicStyle
import com.example.neumorphism.theme.NeumorphicTheme

public data class IconButtonStateStyle(
    public val neumorphicStyle: NeumorphicStyle,
    public val iconColor: Color,
    public val padding: Dp,
) {
    public companion object {
        public fun resolveIdle(cascadingStyle: CascadingIconButtonStateStyle?, theme: NeumorphicTheme, contentColor: Color): IconButtonStateStyle {
            return resolve(cascadingStyle, theme.resolvedHeight(), theme, contentColor)
        }

        public fun resolvePressed(cascadingStyle: CascadingIconButtonStateStyle?, theme: NeumorphicTheme, contentColor: Color): IconButtonStateStyle {
            return resolve(cascadingStyle, -theme.resolvedHeight(), theme, contentColor)
        }

        private fun resolve(
            cascadingStyle: CascadingIconButtonStateStyle?,
            height: Dp,
            theme: NeumorphicTheme,
            contentColor: Color,
        ): IconButtonStateStyle {
            return IconButtonStateStyle(
                neumorphicStyle = resolveNeumorphicStyle(cascadingStyle?.neumorphicStyle, height, theme),
                padding = cascadingStyle?.padding ?: 8.dp,
                iconColor = cascadingStyle?.iconColor ?: contentColor,
            )
        }

        private fun resolveNeumorphicStyle(cascadingStyle: CascadingNeumorphicStyle?, height: Dp, theme: NeumorphicTheme): NeumorphicStyle {
            return NeumorphicStyle(
                shape = cascadingStyle?.shape ?: RoundedCornerShape(3000.dp),
                height = cascadingStyle?.height ?: height,
                intensity = cascadingStyle?.intensity ?: theme.resolvedIntensity(),
                backgroundColor = cascadingStyle?.backgroundColor ?: theme.resolvedColorScheme().background,
                shadowColor = cascadingStyle?.shadowColor ?: theme.resolvedColorScheme().shadow,
                highlightColor = cascadingStyle?.highlightColor ?: theme.resolvedColorScheme().highlight,
            )
        }
    }
}