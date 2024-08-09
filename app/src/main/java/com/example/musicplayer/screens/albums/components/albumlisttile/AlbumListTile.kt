package com.example.musicplayer.screens.albums.components.albumlisttile

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.screens.albums.components.albumlisttile.disc.Disc
import com.example.musicplayer.screens.albums.viewmodel.PreviewViewModel
import com.example.musicplayer.screens.albums.viewmodel.AlbumsPageViewModel
import com.example.musicplayer.screens.albums.viewmodel.uistate.AlbumsListItemUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
internal fun AlbumListTile(uiState: AlbumsListItemUiState, viewModel: AlbumsPageViewModel) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    CustomListTile(
        headlineContent = { Text(uiState.name, maxLines = 1) },
        supportingContent = {
            Text(
                uiState.artist,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                maxLines = 1,
            )
        },
        leadingContent = { CoverArt(uiState.coverArt) },
        trailingContent = {
            Box(modifier = Modifier.wrapContentSize()) {
                IconButton(onClick = { viewModel.albumListItemMoreButtonClicked(uiState.id) }) {
                    Icon(Icons.Default.MoreVert, null)
                }

                DropdownMenu(
                    expanded = uiState.moreMenuOpened,
                    onDismissRequest = { viewModel.albumListItemMoreMenuDismissed(uiState.id) },
                ) {
                    DropdownMenuItem(
                        { Text("Add to Queue") },
                        onClick = { viewModel.songListItemAddToQueueButtonClicked(uiState.id) }
                    )
                }
            }
        },
        onClick = { viewModel.albumListItemClicked(uiState.id) }
    )
}

@Composable
private fun CoverArt(coverArt: Uri?) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    Box(
        modifier = Modifier
            .wrapContentSize()
            .neumorphic(height = 4.dp, shape = RoundedCornerShape(3000.dp))
            .padding(2.dp)
    ) {
        Disc(isSpinning = false) {
            val modifier: Modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(3000.dp))

            if (coverArt != null) {
                AsyncImage(
                    model = coverArt,
                    contentDescription = null,
                    modifier = modifier,
                    fallback = painterResource(R.drawable.songcover),
                )
            }
            else {
                Icon(
                    modifier = modifier.padding(8.dp),
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = theme.resolvedColorScheme().text,
                )
            }
        }
    }
}

@Composable
private fun Thumbnail(uri: Uri) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    if (Build.VERSION.SDK_INT >= VERSION_CODES.Q) {
        val (isLoaded, image) = rememberLoadedThumbnail(uri, 48.dp)

        val modifier: Modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(3000.dp))

        if (!isLoaded) {
            Box(modifier = Modifier.size(48.dp).background(Color.Black))
        }
        else {
            Icon(
                modifier = modifier.padding(8.dp),
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = theme.resolvedColorScheme().text,
            )
        }
    }
}


@RequiresApi(VERSION_CODES.Q)
@Composable
private fun rememberLoadedThumbnail(uri: Uri, dimension: Dp): RememberLoadedThumbnailResult {
    val contentResolver: ContentResolver = LocalContext.current.contentResolver
    var isLoaded by remember { mutableStateOf<Boolean>(false) }
    var bitmap: Bitmap? by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        val size: Size =  Size(dimension.value.toInt(), dimension.value.toInt())
        bitmap = contentResolver.loadThumbnail(uri, size, null)
        isLoaded = true
    }

    return RememberLoadedThumbnailResult(isLoaded, bitmap?.asImageBitmap())
}

private data class RememberLoadedThumbnailResult(
    public val isLoaded: Boolean,
    public val image: ImageBitmap?,
)

@Preview(device = "id:Nexus S")
@Composable
private fun AlbumListTilePreview() {
    val viewModel: AlbumsPageViewModel = remember { PreviewViewModel() }

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

                        val uiState: AlbumsListItemUiState = AlbumsListItemUiState(
                            id = "id_$index",
                            name = "Song $index",
                            artist = "Random Artist",
                            coverArt = null,
                            moreMenuOpened = false,
                        )

                        AlbumListTile(uiState, viewModel)
                    }
                }
            }
        }
    }
}