package com.example.neumorphism.componentspreviewpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.button.Button
import com.example.neumorphism.components.iconbutton.IconButton
import com.example.neumorphism.components.textfield.TextField
import com.example.neumorphism.theme.PreviewThemeProvider

@Preview
@Composable
public fun ComponentsPreviewPage() {
    PreviewThemeProvider {
        Scaffold { paddingValues ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(onClick = {}) { Text("Submit") }

                    IconButton(onClick = {}) { Icon(Icons.Filled.Done, null) }

                    TextField(value = "2001s.yn@gmail.com", onValueChange = { _ -> })
                }
            }
        }
    }
}