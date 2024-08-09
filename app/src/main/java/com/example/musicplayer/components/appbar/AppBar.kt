package com.example.musicplayer.components.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton

@Composable
public fun AppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        navigationIcon()

        Spacer(Modifier.width(16.dp))

        ProvideTextStyle(MaterialTheme.typography.headlineSmall) {
            title()
        }
    }
}

@Preview
@Composable
public fun AppBarPreview() {
    RootThemeProvider {
        Scaffold() { padding ->
            Column(modifier = Modifier.fillMaxSize()) {
                AppBar(
                    navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) } },
                    title = { Text("Home") }
                )

                Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(padding)) { }
            }
        }
    }
}