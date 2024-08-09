package com.example.musicplayer.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.neumorphism.theme.LocalNeumorphicTheme

import androidx.compose.material3.Typography
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.musicplayer.R

private fun buildDarkColorScheme(background: Color, primary: Color, onBackground: Color) = darkColorScheme(
//    primary = Purple80,
    primary = primary,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = background,
    onBackground = onBackground,
)

private fun buildLightColorScheme(background: Color, primary: Color) = lightColorScheme(
//    primary = Purple40,
    primary = primary,
    secondary = PurpleGrey40,
    tertiary = Pink40,
//    background = background,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
private fun setupColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme {
    val neumorphicColorScheme = LocalNeumorphicTheme.current!!.resolvedColorScheme()

    val primaryColor: Color = neumorphicColorScheme.primary

    return when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> {
            buildDarkColorScheme(
                neumorphicColorScheme.background,
                primaryColor,
                neumorphicColorScheme.text
            )
        }

//        darkTheme -> buildDarkColorScheme(neumorphicColorScheme.background, primaryColor)
//        else -> buildLightColorScheme(neumorphicColorScheme.background, primaryColor)
    }
}

private fun setupFontFamily(): FontFamily {
    return FontFamily(
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_bold, FontWeight.Bold),
    )
}

@Composable
internal fun MusicPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = setupColorScheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor,
    )
    val neumorphicColorScheme = LocalNeumorphicTheme.current!!.resolvedColorScheme()

    val fontFamily: FontFamily = setupFontFamily()

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
            .setFontFamily(fontFamily)
            .setHeadingStyles(fontWeight = FontWeight.Bold),
    ) {
        CompositionLocalProvider(
            value = LocalContentColor.provides(neumorphicColorScheme.text),
            content = content,
        )
    }
}

private fun Typography.setHeadingStyles(fontWeight: FontWeight): Typography {
    return this.copy(
        headlineSmall = this.headlineSmall.copy(fontWeight = fontWeight),
        headlineMedium = this.headlineMedium.copy(fontWeight = fontWeight),
        headlineLarge = this.headlineLarge.copy(fontWeight = fontWeight),
    )
}

private fun Typography.setFontFamily(fontFamily: FontFamily): Typography {
    return this.copy(
        bodySmall = this.bodySmall.copy(fontFamily = fontFamily),
        bodyMedium = this.bodyMedium.copy(fontFamily = fontFamily),
        bodyLarge = this.bodyLarge.copy(fontFamily = fontFamily),
        headlineSmall = this.headlineSmall.copy(fontFamily = fontFamily),
        headlineMedium = this.headlineMedium.copy(fontFamily = fontFamily),
        headlineLarge = this.headlineLarge.copy(fontFamily = fontFamily)
    )
}