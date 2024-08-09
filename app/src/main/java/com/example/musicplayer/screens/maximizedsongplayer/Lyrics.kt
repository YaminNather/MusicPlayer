package com.example.musicplayer.screens.maximizedsongplayer

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun Lyrics() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("See you see")
            }

            withStyle(style = SpanStyle(color = Color.Gray)) {
                append(" you seen you every time")
            }
        },
    )
}
