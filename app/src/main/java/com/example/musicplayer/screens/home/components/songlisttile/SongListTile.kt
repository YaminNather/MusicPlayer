package com.example.musicplayer.screens.home.components.songlisttile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
internal fun SongListTile(isPlaying: Boolean) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    Row(
        modifier = Modifier
            .fillMaxWidth().wrapContentHeight()
            .neumorphic(
                height = if (isPlaying) (-4).dp else 0.dp,
                RoundedCornerShape(16.dp),
                backgroundColor = if (isPlaying) Color(0xFF151719) else Color.Transparent,
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .neumorphic(height = 4.dp, shape = RoundedCornerShape(3000.dp))
                .padding(2.dp)
        ) {
            AsyncImage(
                model = "https://cdn.musebycl.io/2021-03/travisscott_astroworld_hed_2021.jpg",
                contentDescription = null,
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(3000.dp)),
                fallback = painterResource(R.drawable.songcover),
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f).wrapContentHeight()) {
            Text(
                "Ain't No Time",
                fontWeight = FontWeight.Bold,
                color = if (isPlaying) theme.resolvedColorScheme().primary else Color.Unspecified,
            )

            Spacer(Modifier.height(4.dp))

            Text("Future", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray))
        }

        IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, null) }
//        PlayPauseButton(isPlaying = isPlaying) { }
    }
}

@Preview
@Composable
private fun SongListTilePreview() {
    RootThemeProvider {
        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(20) { index ->
                        if (index != 0) Spacer(Modifier.height(16.dp))

                        SongListTile(isPlaying = index == 5)
                    }
                }
            }
        }
    }
}