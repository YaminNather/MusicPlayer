package com.example.neumorphism.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.neumorphism.components.button.style.ButtonStyle
import com.example.neumorphism.components.button.style.CascadingButtonStyle
import com.example.neumorphism.components.iconbutton.style.CascadingIconButtonStyle
import com.example.neumorphism.components.iconbutton.style.IconButtonStyle
import com.example.neumorphism.components.textfield.style.CascadingTextFieldStyle

public data class NeumorphicTheme(
    public val height: Dp? = null,
    public val intensity: Float? = null,
    public val colorScheme: CascadingColorScheme? = null,

    public val button: CascadingButtonStyle? = null,
    public val iconButton: CascadingIconButtonStyle? = null,
    public val textField: CascadingTextFieldStyle? = null,
) {
    public fun merge(mergeWith: NeumorphicTheme): NeumorphicTheme {
        return NeumorphicTheme(
            height = mergeWith.height ?: height,
            intensity = mergeWith.intensity ?: intensity,
            colorScheme = colorScheme?.merge(mergeWith.colorScheme) ?: mergeWith.colorScheme,

            button = button?.merge(mergeWith.button) ?: mergeWith.button,
            iconButton = iconButton?.merge(mergeWith.iconButton) ?: mergeWith.iconButton,
            textField = textField?.merge(mergeWith.textField) ?: mergeWith.textField,
        )
    }

    public fun resolvedHeight(): Dp = height ?: 4.dp

    public fun resolvedIntensity(): Float = intensity ?: 12f

    public fun resolvedButtonStyle(): ButtonStyle = ButtonStyle.resolve(button, this)

    public fun resolvedIconButtonStyle(): IconButtonStyle = IconButtonStyle.resolve(iconButton, this)

    public fun resolvedColorScheme(): ColorScheme = ColorScheme.resolve(colorScheme)
}