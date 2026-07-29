package com.axelestrada.dinaco.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun BadgedIcon(
    hasBadge: Boolean,
    modifier: Modifier = Modifier,
    badgeColor: Color = MaterialTheme.colorScheme.primary,
    badgeOffset: DpOffset = DpOffset(x = 0.dp, y = (-2).dp),
    icon: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        icon()

        if (hasBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = badgeOffset.x, y = badgeOffset.y)
                    .size(14.dp)
                    .drawBehind {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    badgeColor.copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            ),
                            radius = size.minDimension / 2f
                        )

                        drawCircle(
                            color = badgeColor,
                            radius = 3.5.dp.toPx()
                        )
                    }
            )
        }
    }
}