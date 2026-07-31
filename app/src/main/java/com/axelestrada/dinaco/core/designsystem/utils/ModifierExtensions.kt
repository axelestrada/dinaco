package com.axelestrada.dinaco.core.designsystem.utils

import android.graphics.BlurMaskFilter
import android.os.Build
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Aplica un efecto de sombra/resplandor configurable.
 */
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
                    this.maskFilter = BlurMaskFilter(
                        blurRadius.toPx(),
                        BlurMaskFilter.Blur.NORMAL
                    )
                }
            }
        }

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

/**
 * Aplica un efecto de glassmorphism con desenfoque de fondo.
 * Nota: El desenfoque de fondo real (RenderEffect) requiere Android 12 (S) o superior.
 */
fun Modifier.glassBlur(
    blurRadius: Dp = 10.dp
) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    this.blur(blurRadius)
} else {
    this
}
