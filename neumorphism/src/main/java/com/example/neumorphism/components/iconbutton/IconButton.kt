package com.example.neumorphism.components.iconbutton

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.iconbutton.style.CascadingIconButtonStyle
import com.example.neumorphism.components.iconbutton.style.IconButtonStateStyle
import com.example.neumorphism.components.iconbutton.style.IconButtonStyle
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme
import com.example.neumorphism.theme.PreviewThemeProvider
import kotlin.math.absoluteValue
import kotlin.math.sign

@Composable
public fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: CascadingIconButtonStyle? = null,
    content: @Composable () -> Unit,
) {
    var isPressed by remember { mutableStateOf<Boolean>(false) }
    val resolvedStateStyle: IconButtonStateStyle = calculateFinalStyle(style, isPressed)

    Box(
        modifier = modifier
            .neumorphic(
                resolvedStateStyle.neumorphicStyle.copy(
                    height = Dp((resolvedStateStyle.neumorphicStyle.height * 0.5f).value.absoluteValue)
                )
            )
            .padding(4.dp)
//            .padding(resolvedStateStyle.padding)
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
    ) {
//        CompositionLocalProvider(LocalContentColor.provides(resolvedStateStyle.iconColor)) {
//            content()
//        }

        Box(
            modifier = Modifier
                .neumorphic(resolvedStateStyle.neumorphicStyle)
                .padding(resolvedStateStyle.padding),
        ) {
           CompositionLocalProvider(LocalContentColor.provides(resolvedStateStyle.iconColor)) {
               content()
           }
        }
    }
}

@Composable
private fun calculateFinalStyle(style: CascadingIconButtonStyle?, isPressed: Boolean): IconButtonStateStyle {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!
    val contentColor: Color = LocalContentColor.current

    val cascadingStyle: CascadingIconButtonStyle? = theme.iconButton?.merge(style) ?: style
    val resolvedStyle: IconButtonStyle = IconButtonStyle.resolve(cascadingStyle, theme)

    return if (!isPressed) resolvedStyle.idle else resolvedStyle.pressed
}

@Preview
@Composable
private fun IconButtonPreview() {
    PreviewThemeProvider {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(onClick = {}) { Icon(Icons.Filled.PlayArrow, null) }
            }
        }
    }
}