package com.example.neumorphism.theme

import androidx.compose.ui.graphics.Color

public data class ColorScheme(
    public val background: Color,
    public val shadow: Color,
    public val highlight: Color,
    public val primary: Color,
    public val accent: Color,
    public val text: Color,
) {
    public companion object {
        public fun resolve(cascadingScheme: CascadingColorScheme?): ColorScheme {
//            return ColorScheme(
//                background = cascadingScheme?.background ?: Color(0xFF222529),
//                shadow = cascadingScheme?.shadow ?: Color(0x88000000),
//                highlight = cascadingScheme?.highlight ?: Color(0xFF666666).copy(alpha = 0.3f),
//                primary = cascadingScheme?.primary ?: Color(0xFFee550e),
//                accent = cascadingScheme?.accent ?: Color.Green,
//                text = cascadingScheme?.text ?: Color(0xFFa7a8aa),
//            )

            return ColorScheme(
                background = cascadingScheme?.background ?: Color(0xFFdfe9fd),
                shadow = cascadingScheme?.shadow ?: Color(0x00000000).copy(alpha = 0.3f),
                highlight = cascadingScheme?.highlight ?: Color(0xFFFFFFFF).copy(alpha = 0.7f),
                primary = cascadingScheme?.primary ?: Color(0xFF7689ff),
                accent = cascadingScheme?.accent ?: Color.Green,
//                text = cascadingScheme?.text ?: Color(0xFFa2b1ca),
                text = cascadingScheme?.text ?: Color(0xFF6b7992),
            )
        }
    }
}