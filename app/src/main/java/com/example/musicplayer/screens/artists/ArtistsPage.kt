package com.example.musicplayer.screens.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.musicplayer.R
import com.example.musicplayer.components.customlisttile.CustomListTile
import com.example.musicplayer.components.customscaffold.CustomScaffold
import com.example.musicplayer.components.customtopappbar.CustomTopAppBar
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.core.modifiers.neumorphic

//@Composable
//public fun ArtistsPage(navHostController: NavHostController, viewModel: ArtistsPageViewModel? = null) {
//    val internalViewModel = remember<ArtistsPageViewModel> {
//       viewModel ?: koinContainer.getValue<ArtistsPageViewModel>()
//    }
//
//    LaunchedEffect(viewModel) {
//        viewModel.pageOpened()
//    }
//
//    CustomScaffold(
//        topBar = {
//            CustomTopAppBar(
//                title = { Text("Artists") },
//                navigationButton = {
//                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, null) }
//                },
//            )
//        },
//    ) { paddingValues ->
//        LazyColumn(modifier = Modifier.padding(16.dp)) {
//            items(5) { index ->
//                CustomListTile(
//                    headlineContent = { Text("Artist Name") },
//                    supportingContent = { Text("X Songs") },
//                    leadingContent = {
//                        Box(modifier = Modifier
//                            .neumorphic()
//                            .padding(2.dp)) {
//                            Image(
//                                painterResource(R.drawable.songcover),
//                                null,
//                            )
//                        }
//                    }
//                )
//            }
//        }
//    }
//}