package com.example.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.businesslogic.Initializer
import com.example.musicplayer.dependencyinjection.initializeKoin
import com.example.musicplayer.dependencyinjection.koinContainer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        initializeKoin(this)

        koinContainer.getValue<Initializer>().initialize()

        setContent {
            App()
        }
    }
}