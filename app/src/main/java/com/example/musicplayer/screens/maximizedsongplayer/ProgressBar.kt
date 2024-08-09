package com.example.musicplayer.screens.maximizedsongplayer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.RootThemeProvider
import com.example.neumorphism.core.modifiers.neumorphic
import kotlin.math.min

@Composable
public fun ProgressBar(
    value: Float,
    modifier: Modifier = Modifier,
    width: Dp = 2.dp,
    content: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .drawWithContent {
                drawArc(
                    color = Color.Red,
                    startAngle = 270f, sweepAngle = value * -180f,
                    useCenter = false,
                    size = Size(this.size.minDimension, this.size.minDimension),
                    style = Stroke(width = width.toPx(), cap = StrokeCap.Round),
                )

                drawContent()
            }
            .layout { measurable, constraints ->
                val diameter: Int = min(constraints.maxWidth, constraints.maxHeight)

                val newConstraints: Constraints = Constraints(0, diameter, 0, diameter)
                val placeable: Placeable = measurable.measure(newConstraints)

                this.layout(diameter, diameter) {
                    placeable.placeRelative(0, 0)
                }
            }
            .then(modifier)
    ) {
        content?.let { it() }
    }

//    Box(modifier = modifier) {
//        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
//            val diameter: Dp = min(this.constraints.maxWidth, this.constraints.maxHeight).dp
//
//            Spacer(
//                modifier = Modifier.size(diameter).drawWithContent {
//                    drawArc(
//                        color = Color.Red,
//                        startAngle = 270f, sweepAngle = value * -180f,
//                        useCenter = false,
//                        size = Size(this.size.minDimension, this.size.minDimension),
//                        style = Stroke(width = width.toPx(), cap = StrokeCap.Round),
//                    )
//
//                    drawContent()
//                }
//            )
//        }
//    }
}

@Preview
@Composable
private fun ProgressBarPreview() {
    RootThemeProvider {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
            ) {
                ProgressBar(value = 1f, width = 2.dp, modifier = Modifier.padding(32.dp)) {
                   Box(
                       modifier = Modifier
                           .fillMaxSize()
                           .neumorphic(shape = RoundedCornerShape(3000.dp)),
                       contentAlignment = Alignment.Center
                   ) {
                       Text("Yamin Nather")
                   }
                }
            }
        }
    }
}
