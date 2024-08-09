package com.example.musicplayer.screens.neumorphictest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.RootThemeProvider

import com.example.neumorphism.components.button.Button
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
public fun NeumorphicTrialPage() {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!
    Box(
        modifier = Modifier
            .background(theme.resolvedColorScheme().background)
            .fillMaxSize()
            .padding(all = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Button(onClick = {}) {
            Text("Click me")
        }
    }
}

@Preview
@Composable
private fun NeumorphicTrialPagePreview() {
    RootThemeProvider {
        Scaffold { _ ->
            NeumorphicTrialPage()
        }
    }
}