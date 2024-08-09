package com.example.musicplayer.components.navigationdrawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme

@Composable
public fun NavigationDrawerItem(
    isSelected: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    var isPressed by remember { mutableStateOf<Boolean>(false) }

    val contentColor: Color =
        if (isSelected) theme.resolvedColorScheme().primary
        else theme.resolvedColorScheme().text

    val height: Dp =
        if (isSelected || isPressed) -theme.resolvedHeight()
        else theme.resolvedHeight()

    Box(
        modifier = Modifier
            .wrapContentSize()
            .neumorphic(height = height, shape = RoundedCornerShape(3000.dp))
            .pointerInput(Unit) {
                this.detectTapGestures(
                    onTap = { _ -> onClick() },
                    onPress = {
                        isPressed = true
                        awaitRelease()
                        isPressed = false
                    }
                )
            }
            .padding(8.dp),
    ) {
        CompositionLocalProvider(LocalContentColor.provides(contentColor)) {
            content()
        }
    }
}

@Preview(device = "id:Nexus S")
@Composable
private fun NavigationDrawerItemPreview() {
    RootThemeProvider {
        val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

        Box(
            modifier = Modifier
                .width(128.dp)
                .height(256.dp)
                .background(theme.resolvedColorScheme().background),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                NavigationDrawerItem(onClick = {}) {
                    Icon(Icons.Default.Home, null)
                }

                Spacer(Modifier.height(16.dp))

                NavigationDrawerItem(isSelected = true, onClick = {}) {
                    Icon(Icons.Default.Home, null)
                }
            }
        }
    }
}