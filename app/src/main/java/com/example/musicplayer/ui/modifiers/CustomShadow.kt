package com.example.musicplayer.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

public fun Modifier.customShadow(
    blurRadius: Dp = 0.dp,
    offset: DpOffset = DpOffset.Zero,
    color: Color = Color.Black,
    shape: Shape = RectangleShape,
): Modifier {
    return drawBehind {
        drawIntoCanvas { canvas ->
            val paint: Paint = Paint()
            val frameworkPaint: NativePaint = paint.asFrameworkPaint()
            frameworkPaint.color = Color.Transparent.toArgb()
            frameworkPaint.setShadowLayer(
                blurRadius.toPx(),
                offset.x.toPx(), offset.y.toPx(),
                color.toArgb()
            )

            val outline: Outline = shape.createOutline(this.size, this.layoutDirection, this)
            canvas.drawOutline(outline, paint)
        }
    }
}

//public fun Modifier.customShadow(
//    blurRadius: Dp = 0.dp,
//    offset: DpOffset = DpOffset.Zero,
//    color: Color = Color.Black,
//    shape: Shape = RectangleShape,
//): Modifier {
//    return graphicsLayer {
//        this.shadowElevation = blurRadius.toPx()
//        this.translationX = offset.x.toPx()
//        this.translationY = offset.y.toPx()
//        this.ambientShadowColor = color
//        this.spotShadowColor = color
//    }
//}