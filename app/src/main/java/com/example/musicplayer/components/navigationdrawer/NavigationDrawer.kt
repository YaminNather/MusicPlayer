package com.example.musicplayer.components.navigationdrawer

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
internal fun NavigationDrawer(
    modifier: Modifier = Modifier,
    state: NavigationDrawerHostedState = rememberNavigationDrawerHostedState(),
    items: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    Layout(
        content = {
            InternalNavigationDrawer(items = items)

            content()
        }
    ) { measurables, constraints ->
        val placeables: List<Placeable> = measurables.map { element -> element.measure(constraints) }

        layout(constraints.maxWidth, constraints.maxHeight) {
            for (i in placeables.indices) {
                if (i == 0) {
                    placeables[i].placeRelative(IntOffset.Zero)
                }
                else {
                    val xPosition: Int = (state.contentOffsetFactor * placeables[0].width).toInt()
                    placeables[i].placeRelative(xPosition, 0)
                }
            }
        }
    }
}

@Composable
private fun InternalNavigationDrawer(items: @Composable ColumnScope.() -> Unit) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(theme.resolvedColorScheme().background)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            content = items,
        )
    }
}


@Preview(device = "id:Nexus S")
@Composable
private fun NavigationDrawerPreview() {
    val navigationDrawerState: NavigationDrawerHostedState = rememberNavigationDrawerHostedState()

    RootThemeProvider {
        NavigationDrawer(
            items = {
                NavigationDrawerItem(isSelected = true, onClick = {}) {
                    Icon(Icons.Default.Home, null)
                }

                NavigationDrawerItem(onClick = {}) { Icon(Icons.Default.MusicNote, null) }

                NavigationDrawerItem(onClick = {}) { Icon(Icons.Default.Equalizer, null) }

                NavigationDrawerItem(onClick = {}) { Icon(Icons.Default.Settings, null) }
            }
        ) {
            CustomScaffold(
                topBar = {
                    CustomTopAppBar(
                        navigationButton = {
                            IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) }
                        },
                        title = { Text("Test") },
                        actions = {
                            IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
                        }
                    )
                },
            ) { _ ->

            }
        }
    }
}