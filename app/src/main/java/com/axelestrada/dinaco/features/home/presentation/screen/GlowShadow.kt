package com.axelestrada.dinaco.features.home.presentation.screen

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.glowShadow(
    color: Color,
    blurRadius: Dp = 40.dp,
    offsetY: Dp = 0.dp,
    spread: Dp = 0.dp,
    isCircle: Boolean = true
) = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            asFrameworkPaint().apply {
                this.color = color.toArgb()
                if (blurRadius.toPx() > 0) {
                    // BlurMaskFilter.Blur.NORMAL extiende el brillo hacia afuera sin recortar
                    this.maskFilter = BlurMaskFilter(
                        blurRadius.toPx(),
                        BlurMaskFilter.Blur.NORMAL
                    )
                }
            }
        }

        // Calculamos la posición aplicando el offset y el spread
        val spreadPx = spread.toPx()
        val offsetYPx = offsetY.toPx()

        val left = spreadPx
        val top = offsetYPx + spreadPx
        val right = size.width - spreadPx
        val bottom = size.height + offsetYPx - spreadPx

        if (isCircle) {
            val radius = (right - left) / 2f
            val centerX = size.width / 2f
            val centerY = (top + bottom) / 2f
            canvas.drawCircle(
                center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                radius = radius,
                paint = paint
            )
        } else {
            canvas.drawRect(
                left = left,
                top = top,
                right = right,
                bottom = bottom,
                paint = paint
            )
        }
    }
}