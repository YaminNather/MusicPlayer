package com.example.neumorphism.components.button

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.button.style.ButtonStateStyle
import com.example.neumorphism.components.button.style.ButtonStyle
import com.example.neumorphism.components.button.style.CascadingButtonStateStyle
import com.example.neumorphism.components.button.style.CascadingButtonStyle
import com.example.neumorphism.core.styles.CascadingNeumorphicStyle
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme
import com.example.neumorphism.theme.PreviewThemeProvider

@Composable
public fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: CascadingButtonStyle? = null,
    content: @Composable () -> Unit,
) {
    val isPressed: MutableState<Boolean> = remember { mutableStateOf<Boolean>(false) }

    val finalStyle: ButtonStyle = calculateFinalStyle(style)
    val stateStyle: ButtonStateStyle = calculateFinalStateStyle(finalStyle, isPressed.value)

    val height by animateDpAsState(stateStyle.neumorphicStyle.height, label = "height_transition")

    Box(
        modifier = Modifier
            .defaultMinSize(stateStyle.sizeConstraints.minWidth, stateStyle.sizeConstraints.minHeight)
            .neumorphic(stateStyle.neumorphicStyle.copy(height = height))
            .padding(stateStyle.padding)
            .pointerInput(Unit) {
                this.detectTapGestures(
                    onTap = { _ -> onClick() },
                    onPress = {
                        isPressed.value = true
                        awaitRelease()
                        isPressed.value = false
                    }
                )
            }
            .then(modifier)
    ) {
        CompositionLocalProvider(LocalContentColor.provides(stateStyle.foregroundColor)) {
            content()
        }
    }
}

@Composable
private fun calculateFinalStyle(style: CascadingButtonStyle?): ButtonStyle {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!
    val finalCascadingStyle: CascadingButtonStyle? = style?.merge(theme.button) ?: theme.button

    return ButtonStyle.resolve(finalCascadingStyle, theme)
}

@Composable
private fun calculateFinalStateStyle(style: ButtonStyle, isPressed: Boolean): ButtonStateStyle {
    return if (isPressed) {
        style.pressed
    }
    else {
        style.idle
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    PreviewThemeProvider {
        val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

        Scaffold() { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = {}) {
                    Text("CLICK HERE", fontWeight = FontWeight.Bold)
                }

                Box(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {},
                    style = CascadingButtonStyle(
                        idle = CascadingButtonStateStyle(
                            neumorphicStyle = CascadingNeumorphicStyle(
                                height = (-4).dp,
                                highlightColor = Color.White,
                            ),
                        )
                    ),
                ) {
                    Text("CLICK HERE", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}