package com.example.neumorphism.core.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

public fun Modifier.customShadow(
    blurRadius: Dp = 0.dp,
    offset: DpOffset = DpOffset.Zero,
    color: Color = Color.Black,
    shape: Shape = RectangleShape,
): Modifier {
    return drawWithContent {
        this.drawIntoCanvas() { canvas ->
            val paint: Paint = Paint()
            paint.isAntiAlias = true
            paint.asFrameworkPaint().apply {
                this.color = Color.Transparent.toArgb()

                this.setShadowLayer(
                    blurRadius.toPx(),
                    offset.x.toPx(), offset.y.toPx(),
                    color.toArgb(),
                )
            }

            val outline: Outline = shape.createOutline(this.size, layoutDirection, this)
            canvas.drawOutline(outline, paint)
        }

        this.drawContent()
    }
}