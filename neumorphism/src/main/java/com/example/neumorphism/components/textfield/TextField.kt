package com.example.neumorphism.components.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextField as MaterialTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.textfield.style.CascadingTextFieldStyle
import com.example.neumorphism.components.textfield.style.TextFieldStyle
import com.example.neumorphism.core.modifiers.neumorphic
import com.example.neumorphism.theme.LocalNeumorphicTheme
import com.example.neumorphism.theme.NeumorphicTheme
import com.example.neumorphism.theme.PreviewThemeProvider

@Composable
public fun TextField(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    style: CascadingTextFieldStyle? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val finalStyle: TextFieldStyle = calculateFinalStyle(cascadingStyle = style)

    MaterialTextField(
        modifier = modifier
            .neumorphic(finalStyle.neumorphicStyle),
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        colors = calculateMaterialTextFieldColors(),
        textStyle = TextStyle(color = finalStyle.textColor),
        trailingIcon = trailingIcon,
    )
}

@Composable
public fun TextField(
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    style: CascadingTextFieldStyle? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val finalStyle: TextFieldStyle = calculateFinalStyle(cascadingStyle = style)

    MaterialTextField(
        modifier = modifier
            .neumorphic(finalStyle.neumorphicStyle),
        colors = calculateMaterialTextFieldColors(),
        value = value,
        placeholder = placeholder,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = finalStyle.textColor),
        trailingIcon = trailingIcon,
    )
}

@Composable
private fun calculateFinalStyle(cascadingStyle: CascadingTextFieldStyle?): TextFieldStyle {
    val theme: NeumorphicTheme = LocalNeumorphicTheme.current!!

    return TextFieldStyle.resolve(cascadingStyle?.merge(theme.textField), theme)
}

@Composable
private fun calculateMaterialTextFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        disabledContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledBorderColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
        errorBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
    )
}

@Preview
@Composable
private fun TextFieldPreview() {
    var textFieldValue by remember { mutableStateOf<TextFieldValue>(TextFieldValue()) }

    PreviewThemeProvider {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                Column {
                    Text("Email")

                    Spacer(Modifier.height(8.dp))

                    TextField(
                        textFieldValue,
                        { value -> textFieldValue = value },
                        placeholder = { Text("john.doe@gmail.com") }
                    )

                    Spacer(Modifier.height(16.dp))

                    TextField(
                        value = "Yamin",
                        { _ -> },
                        trailingIcon = { Icon(Icons.Default.Search, null) }
                    )
                }
            }
        }
    }
}