package com.axelestrada.dinaco.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.R

@Composable
fun NotificationsButton() {
    val interactionSource = remember { MutableInteractionSource() }

    val pressed by interactionSource.collectIsPressedAsState()

    val dotColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .scale(if (pressed) 0.95f else 1f)
            .background(
                MaterialTheme.colorScheme.surface, CircleShape
            )
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape)
            .padding(14.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { /* TODO: Navigate to notifications screen */ })
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_lucide_bell),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(20.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 0.dp, y = (-4).dp)
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.Center)
                    .drawBehind {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    dotColor.copy(alpha = 0.3f),
                                    dotColor.copy(alpha = 0f)
                                )
                            ),
                            radius = size.minDimension
                        )
                    }
            )

            Box(
                modifier = Modifier
                    .size(7.dp)
                    .align(Alignment.Center)
                    .background(dotColor, CircleShape)
            )
        }
    }
}

@Preview(name = "NotificationsButtonPreview", showBackground = true)
@Composable
fun NotificationsButtonPreview() {
    NotificationsButton()
}