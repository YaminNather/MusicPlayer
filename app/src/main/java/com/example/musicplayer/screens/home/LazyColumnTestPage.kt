package com.example.musicplayer.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.screens.home.components.songlisttile.SongListTile
import com.example.musicplayer.screens.home.components.songcard.SongCard
import com.example.musicplayer.ui.theme.RootThemeProvider

@Composable
public fun LazyColumnTestPage() {
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
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
                    modifier = Modifier.padding(horizontal = 16.dp).wrapContentSize(),
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

@Preview
@Composable
public fun LazyColumnTestPagePreview() {
    RootThemeProvider {
        LazyColumnTestPage()
    }
}