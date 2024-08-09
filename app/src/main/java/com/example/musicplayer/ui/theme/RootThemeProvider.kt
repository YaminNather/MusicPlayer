package com.example.musicplayer.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.button.style.CascadingButtonStateStyle
import com.example.neumorphism.components.button.style.CascadingButtonStyle
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.theme.CascadingColorScheme
import com.example.neumorphism.theme.NeumorphicTheme
import com.example.neumorphism.theme.ThemeProvider

@Composable
public fun RootThemeProvider(content: @Composable () -> Unit) {
    val theme: NeumorphicTheme = generateTheme()

    ThemeProvider(theme = theme) {
        MusicPlayerTheme(dynamicColor = false, content = content)
    }
}

private fun generateTheme(): NeumorphicTheme {
    val buttonStateStyle: CascadingButtonStateStyle = CascadingButtonStateStyle(
        neumorphicStyle = CascadingNeumorphicStyle(
            shape = RoundedCornerShape(8.dp),
        ),
    )

    return NeumorphicTheme(
        button = CascadingButtonStyle(buttonStateStyle, buttonStateStyle, buttonStateStyle)
    )
}
