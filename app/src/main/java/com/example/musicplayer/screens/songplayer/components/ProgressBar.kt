package com.example.musicplayer.screens.songplayer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
public fun ProgressBar(value: Float, onValueChange: (value: Float) -> Unit) {
    var isValueChanging by remember { mutableStateOf<Boolean>(false) }
    var displayedValue by remember { mutableFloatStateOf(value) }

    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    Slider(
        value = if (!isValueChanging) value else displayedValue,
        onValueChange = { newValue ->
             displayedValue = newValue

            if (!isValueChanging) isValueChanging = true

        },
        onValueChangeFinished = {
            onValueChange(displayedValue)
            isValueChanging = false
        },
        colors = SliderDefaults.colors(
            inactiveTrackColor = theme.resolvedColorScheme().background,
        ),
    )
}