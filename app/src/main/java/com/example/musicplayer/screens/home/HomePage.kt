package com.example.musicplayer.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Scaffold

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.math.MathUtils
import com.example.musicplayer.screens.home.components.songlisttile.SongListTile
import com.example.musicplayer.screens.home.components.songcard.SongCard
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.theme.LocalNeumorphicTheme

import android.graphics.Color as AndroidColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomePage() {
    val backgroundColor: Color = LocalNeumorphicTheme.current!!.resolvedColorScheme().background

    Scaffold(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor,
                        backgroundColor.offsetValue(-0.08f)
                    )
                ),
            ),
        containerColor = Color.Transparent,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth().wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) }

                Text("Songs", style = MaterialTheme.typography.headlineSmall)

//                TopAppBar(
//                    title = {},
//                    navigationIcon = {
//                        IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) }
//                    },
//                    actions = {
//                        IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
//                    },
//                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
//                )
                IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth().wrapContentHeight()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text("Recently Played", style = MaterialTheme.typography.headlineSmall)
                }

                Spacer(Modifier.height(16.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    items(5) { _ ->
                        SongCard()
                    }
                }
            }

            item {
                Text(
                    "All",
                    modifier = Modifier
                        .padding(horizontal = 16.dp).padding(top = 16.dp)
                        .wrapContentSize(),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            items(10) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = if (index == 0) 0.dp else 8.dp)
                ) {
                    SongListTile(isPlaying = index == 4)
                }
            }
        }
    }
}

private val pagePadding: Dp = 32.dp

private fun Color.offsetValue(value: Float): Color {
    val colorHsv: FloatArray = FloatArray(3)
    AndroidColor.colorToHSV(this.toArgb(), colorHsv)

    return Color.hsv(colorHsv[0], colorHsv[1], MathUtils.clamp(colorHsv[2] + value, 0f, 1f))
}

@Composable
private fun HomePagePreview() {
    RootThemeProvider {
        HomePage()
    }
}

@Preview(device = "id:Nexus S")
@Composable
private fun NexusPreview() {
    HomePagePreview()
}

@Preview
@Composable
private fun DefaultPreview() {
    HomePagePreview()
}