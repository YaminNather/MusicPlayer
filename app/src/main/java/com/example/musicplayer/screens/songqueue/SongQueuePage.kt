package com.example.musicplayer.screens.songqueue

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.songqueue.components.songqueuelistitem.SongQueueListItem
import com.example.musicplayer.screens.songqueue.viewmodel.PreviewViewModel
import com.example.musicplayer.screens.songqueue.viewmodel.SongQueuePageViewModel
import com.example.musicplayer.screens.songqueue.viewmodel.uistate.SongQueuePageUiState
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.components.iconbutton.IconButton

@Composable
internal fun SongQueuePage(viewModel: SongQueuePageViewModel? = null) {
    val internalViewModel = remember {
        viewModel ?: koinContainer.getValue<SongQueuePageViewModel>()
    }

    val uiState: SongQueuePageUiState by internalViewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        internalViewModel.pageOpened()

        onDispose{ internalViewModel.closed() }
    }

    CustomScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                navigationButton = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Default.KeyboardArrowLeft, null)
                    }
                },
                title = { Text("Queue") },
            )
        }
    ) { padding ->
        if (uiState.songQueueItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Queue is empty")
            }

            return@CustomScaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(vertical = 16.dp),
        ) {
            items(uiState.songQueueItems.size) { index ->
                SongQueueListItem(uiState.songQueueItems[index], internalViewModel)
            }
        }
    }
}

//@Preview
@Preview(device = "spec:width=1080px,height=2246px,dpi=480", showSystemUi = true)
@Composable
private fun SongQueuePagePreview() {
    RootThemeProvider {
        SongQueuePage(viewModel = PreviewViewModel())
    }
}