package com.example.neumorphism.core.modifiers

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

public fun Modifier.innerShadow(
    color: Color = Color.Black,
    shape: Shape = RoundedCornerShape(8.0.dp),
    offset: DpOffset = DpOffset(4.dp, 4.dp),
    blurRadius: Dp = 0.dp,
): Modifier {
    return this.drawWithContent {
        this.drawContent()

        drawIntoCanvas { canvas ->
            val paint: Paint = Paint().apply {
                this.color = color
                this.isAntiAlias = true
            }

            canvas.saveLayer(Rect(Offset.Zero, this.size), paint)

            val outline: Outline = shape.createOutline(this.size, this.layoutDirection, this)
            canvas.drawOutline(outline, paint)

            paint.asFrameworkPaint().apply {
                this.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

                if (blurRadius.toPx() > 0) {
//                    this.maskFilter = BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
                    this.maskFilter = BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
                }
            }

            paint.color = Color.Black

            canvas.translate(offset.x.toPx(), offset.y.toPx())
            canvas.drawOutline(outline, paint)

            canvas.restore()
        }
    }
}