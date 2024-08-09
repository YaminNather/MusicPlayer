package com.example.neumorphism.components.button.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.core.styles.NeumorphicStyle
import com.example.neumorphism.theme.ColorScheme
import com.example.neumorphism.theme.NeumorphicTheme


public data class ButtonStateStyle(
    public val neumorphicStyle: NeumorphicStyle,
    public val sizeConstraints: ButtonSizeConstraints,
    public val padding: PaddingValues,
    public val foregroundColor: Color
) {
    public companion object {
        public fun resolveToIdle(cascadingStyle: CascadingButtonStateStyle?, theme: NeumorphicTheme): ButtonStateStyle {
            return resolve(cascadingStyle, theme.resolvedHeight(), theme)
        }

        public fun resolveToFocused(cascadingStyle: CascadingButtonStateStyle?, theme: NeumorphicTheme): ButtonStateStyle {
            return resolve(cascadingStyle, theme.resolvedHeight(), theme)
        }

        public fun resolveToPressed(cascadingStyle: CascadingButtonStateStyle?, theme: NeumorphicTheme): ButtonStateStyle {
            return resolve(cascadingStyle, -theme.resolvedHeight(), theme)
        }

        private fun resolve(cascadingStyle: CascadingButtonStateStyle?, height: Dp, theme: NeumorphicTheme): ButtonStateStyle {
            return ButtonStateStyle(
                neumorphicStyle = resolveNeumorphicStyle(cascadingStyle?.neumorphicStyle, height, theme),
                sizeConstraints = ButtonSizeConstraints.resolve(cascadingStyle?.constraints),
                padding = cascadingStyle?.padding ?: PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                foregroundColor = Color(0xFFb9c2d4),
            )
        }

        private fun resolveNeumorphicStyle(cascadingStyle: CascadingNeumorphicStyle?, height: Dp, theme: NeumorphicTheme): NeumorphicStyle {
            val colorScheme: ColorScheme = theme.resolvedColorScheme()

            return NeumorphicStyle(
                height = cascadingStyle?.height ?: height,
                shape = cascadingStyle?.shape ?: RoundedCornerShape(3000.dp),
                backgroundColor = cascadingStyle?.backgroundColor ?: colorScheme.background,
                shadowColor = cascadingStyle?.shadowColor ?: colorScheme.shadow,
                highlightColor = cascadingStyle?.highlightColor ?: colorScheme.highlight,
                intensity = cascadingStyle?.intensity ?: theme.resolvedIntensity(),
            )
        }
    }
}