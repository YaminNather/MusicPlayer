package com.example.neumorphism.core.styles

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.button.style.ButtonSizeConstraints
import com.example.neumorphism.theme.NeumorphicTheme

public data class NeumorphicStyle(
    public val height: Dp,
    public val shape: Shape,
    public val backgroundColor: Color,
    public val shadowColor: Color,
    public val highlightColor: Color,
    public val intensity: Float,
) {
    public companion object {
        public fun resolve(cascadingStyle: CascadingNeumorphicStyle?, theme: NeumorphicTheme): NeumorphicStyle {
            return NeumorphicStyle(
                height = cascadingStyle?.height ?: 2.dp,
                shape = cascadingStyle?.shape ?: RectangleShape,
                backgroundColor = cascadingStyle?.backgroundColor ?: theme.resolvedColorScheme().background,
                shadowColor = cascadingStyle?.shadowColor ?: Color.Black,
                highlightColor = cascadingStyle?.highlightColor ?: Color.White,
                intensity = cascadingStyle?.intensity ?: 4f,
            )
        }
    }
}
