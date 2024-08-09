package com.example.musicplayer.components.customtopappbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton

@Composable
public fun CustomTopAppBar(
    navigationButton: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            navigationButton?.invoke()

            actions?.invoke()
        }

//        CompositionLocalProvider(LocalTextStyle.provides(MaterialTheme.typography.headlineSmall)) {
        CompositionLocalProvider(
            LocalTextStyle
                .provides(MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
        ) {
            title?.invoke()
        }
    }
}

@Preview
@Composable
private fun CustomTopAppBarPreview() {
    RootThemeProvider {
        CustomScaffold(
            topBar = {
                CustomTopAppBar(
                    title = { Text("TEST") },
                    navigationButton = {
                        IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) }
                    },
                    actions = {
                        IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues))
        }
    }
}