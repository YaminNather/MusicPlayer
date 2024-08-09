package com.example.neumorphism.core.modifiers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.core.styles.NeumorphicStyle
import com.example.neumorphism.theme.ColorScheme
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme
import com.example.neumorphism.theme.PreviewThemeProvider

public fun Modifier.neumorphic(
    height: Dp? = null,
    shape: Shape? = null,
    backgroundColor: Color? = null,
    shadowColor: Color? = null,
    highlightColor: Color? = null,
    intensity: Float? = null,
): Modifier = composed {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!
    val resolvedColorScheme = theme.resolvedColorScheme()

    val neumorphicStyle: NeumorphicStyle = NeumorphicStyle(
        height = height ?: theme.resolvedHeight(),
        shape = shape ?: RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor ?: resolvedColorScheme.background,
        shadowColor = shadowColor ?: resolvedColorScheme.shadow,
        highlightColor = highlightColor ?: resolvedColorScheme.highlight,
        intensity = intensity ?: theme.resolvedIntensity(),
    )

    neumorphic(neumorphicStyle)
}

public fun Modifier.neumorphic(style: CascadingNeumorphicStyle): Modifier {
    return this.neumorphic(
        height = style.height,
        shape = style.shape,
        backgroundColor = style.backgroundColor,
        shadowColor = style.shadowColor,
        highlightColor = style.highlightColor,
        intensity = style.intensity,
    )
}

public fun Modifier.neumorphic(style: NeumorphicStyle): Modifier {
    return this
        .neumorphicShadow(style)
        .background(style.backgroundColor, style.shape)
}

private fun Modifier.neumorphicShadow(style: NeumorphicStyle): Modifier {
    if (style.height == 0.dp) {
        return this
    }
    else if (style.height > 0.dp) {
        return this
            .customShadow(
                offset = DpOffset(style.height, style.height),
                blurRadius = style.intensity.dp,
                color = style.shadowColor,
                shape = style.shape,
            )
            .customShadow(
                offset = DpOffset(-style.height, -style.height),
                blurRadius = style.intensity.dp,
                color = style.highlightColor,
                shape = style.shape,
            )
    }
    else {
        return this
            .innerShadow(
                offset = DpOffset(-style.height, -style.height),
                blurRadius = style.intensity.dp,
                color = style.shadowColor,
                shape = style.shape,
            )
            .innerShadow(
                offset = DpOffset(style.height, style.height),
                blurRadius = style.intensity.dp,
                color = style.highlightColor,
                shape = style.shape,
            )
    }
}

@Preview
@Composable
private fun NeumorphicPreview() {
    PreviewThemeProvider {
        val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .neumorphic()
                        .padding(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .neumorphic(
                                NeumorphicStyle(
                                    height= (-6).dp,
                                    intensity = 4f,
                                    shape = RoundedCornerShape(3000.dp),
                                    backgroundColor = theme.resolvedColorScheme().background,
                                    shadowColor = theme.resolvedColorScheme().shadow,
                                    highlightColor = theme.resolvedColorScheme().highlight,
                                )
                            )
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(48.dp),
                    ) {
                        Icon(Icons.Filled.PlayArrow, null)
                        Icon(Icons.Filled.PlayArrow, null)
                        Icon(Icons.Filled.PlayArrow, null)
                        Icon(Icons.Filled.PlayArrow, null)
                    }
                }
            }
        }
    }
}