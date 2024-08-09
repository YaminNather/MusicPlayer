package com.example.neumorphism.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
public fun ThemeProvider(theme: NeumorphicTheme, content: @Composable () -> Unit) {
    val upperTheme: NeumorphicTheme? = LocalNeumorphicTheme.current

    val finalTheme: NeumorphicTheme = upperTheme?.merge(theme) ?: theme

    CompositionLocalProvider(LocalNeumorphicTheme.provides(finalTheme)) {
        content()
    }
}

@Composable
internal fun PreviewThemeProvider(content: @Composable () -> Unit) {
    val neumorphicTheme: NeumorphicTheme = previewTheme()

    ThemeProvider(theme = neumorphicTheme) {
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                background = neumorphicTheme.resolvedColorScheme().background,
            ),
            content = content,
        )
    }
}

internal fun previewTheme(): NeumorphicTheme {
    return NeumorphicTheme()
}
