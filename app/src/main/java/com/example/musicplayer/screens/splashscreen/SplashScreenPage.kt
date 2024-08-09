package com.example.musicplayer.screens.splashscreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.businesslogic.playlistapi.PlaylistApi
import com.example.musicplayer.dependencyinjection.koinContainer
import com.example.musicplayer.screens.PageRoutes

@Composable
public fun SplashScreenPage(navHostController: NavHostController) {
    val context: Activity = LocalContext.current as Activity

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    val permissionRequestState: PermissionRequestState = rememberPermissionRequest()

    LaunchedEffect(permissionRequestState) {
        if (permissionRequestState == PermissionRequestState.Pending) return@LaunchedEffect

        if (permissionRequestState == PermissionRequestState.Denied) {
            context.finish()
        }

        if (permissionRequestState == PermissionRequestState.Granted) {
            navHostController.navigate(PageRoutes.songs)
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading")
        }
    }
}

@Composable
private fun rememberPermissionRequest(): PermissionRequestState {
    val context: Context = LocalContext.current

    var permissionRequestState by remember {
        mutableStateOf<PermissionRequestState>(PermissionRequestState.Pending)
    }

    val permissionRequestLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { result ->
        val arePermissionsGranted: Boolean = !result.values.contains(false)

        permissionRequestState =
            if (arePermissionsGranted) PermissionRequestState.Granted
            else PermissionRequestState.Denied
    }

    LaunchedEffect(Unit) {
        val permissions: List<String> = listOf(
            if (android.os.Build.VERSION.SDK_INT <= 32) Manifest.permission.READ_EXTERNAL_STORAGE
            else Manifest.permission.READ_MEDIA_AUDIO,
        )

        val ungrantedPermissions: List<String> = permissions.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }

        if (ungrantedPermissions.isEmpty()) {
            permissionRequestState = PermissionRequestState.Granted
        }
        else {
            permissionRequestLauncher.launch(ungrantedPermissions.toTypedArray())
        }
    }

    return permissionRequestState
}

private enum class PermissionRequestState {
    Pending,
    Granted,
    Denied,
}

@Composable
private fun rememberCreatedBuiltInPlaylists(): Boolean {
    var didFinish by remember { mutableStateOf<Boolean>(false) }

    LaunchedEffect(Unit) {
        koinContainer.getValue<PlaylistApi>().createBuiltInPlaylists()
        didFinish = true
    }

    return didFinish
}

@Preview
@Composable
public fun SplashScreenPagePreview() {
    val navHostController: NavHostController = rememberNavController()

    SplashScreenPage(navHostController = navHostController)
}