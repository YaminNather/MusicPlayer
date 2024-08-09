package com.example.neumorphism.theme

import androidx.compose.ui.graphics.Color

public data class CascadingColorScheme(
    public val background: Color? = null,
    public val shadow: Color? = null,
    public val highlight: Color? = null,
    public val primary: Color? = null,
    public val accent: Color? = null,
    public val text: Color? = null,
) {
    public fun merge(mergeWith: CascadingColorScheme?): CascadingColorScheme {
        return CascadingColorScheme(
            background = mergeWith?.background ?: background,
            shadow = mergeWith?.shadow ?: shadow,
            highlight = mergeWith?.highlight ?: highlight,
            primary = mergeWith?.primary ?: primary,
            accent = mergeWith?.accent ?: accent,

            text = mergeWith?.text ?: text,
        )
    }
}