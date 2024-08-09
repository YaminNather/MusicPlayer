package com.example.musicplayer.screens.home.components.songcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.neumorphism.core.modifiers.neumorphic

@Composable
internal fun SongCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.wrapContentSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .width(128.dp)
                .height(128.dp)
                .neumorphic(shape = RoundedCornerShape(3000.dp))
                .padding(2.dp),
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(3000.dp)),
                model = "https://marketplace.canva.com/EAFy2GgsPAo/2/0/1600w/canva-red-minimalist-creative-man-without-head-album-cover-_bB_o4a7jdE.jpg",
                contentDescription = null,
                placeholder = painterResource(R.drawable.songcover),
            )
        }

        Spacer(Modifier.height(16.dp))

        Text("Bad Liar", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(4.dp))

        Text("Anna Hamilton", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
    }
}

@Preview(device = "id:Nexus 5")
@Composable
private fun SongCardPreview() {
    RootThemeProvider {
        Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    items(5) { _ ->
                        SongCard(
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}