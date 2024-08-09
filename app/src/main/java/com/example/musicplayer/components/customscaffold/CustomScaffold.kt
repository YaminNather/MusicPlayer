package com.example.musicplayer.components.customscaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.math.MathUtils
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.theme.LocalNeumorphicTheme

@Composable
internal fun CustomScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.backgroundGradient(),
        containerColor = Color.Transparent,
        topBar = topBar,
        content = content,
        snackbarHost = snackbarHost,
    )
}

@Composable
private fun Modifier.backgroundGradient(): Modifier {
    val backgroundColor: Color = LocalNeumorphicTheme.current!!.resolvedColorScheme().background

    return this.background(
        brush = Brush.verticalGradient(
            colors = listOf(
                backgroundColor,
                backgroundColor.offsetValue(-0.08f)
            )
        ),
    )
}

private fun Color.offsetValue(value: Float): Color {
    val colorHsv: FloatArray = FloatArray(3)
    android.graphics.Color.colorToHSV(this.toArgb(), colorHsv)

    return Color.hsv(colorHsv[0], colorHsv[1], MathUtils.clamp(colorHsv[2] + value, 0f, 1f))
}

@Preview
@Composable
private fun CustomScaffoldPreview() {
    RootThemeProvider {
        CustomScaffold { }
    }
}