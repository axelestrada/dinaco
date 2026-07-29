package com.axelestrada.dinaco.core.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.core.navigation.Destination

@Composable
fun GlassBottomBar(
    items: List<BottomBarItem>, currentDestination: Destination, onItemClick: (Destination) -> Unit
) {

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.drawBehind {
            drawLine(
                color = Color(0xFF252525),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        },
        tonalElevation = 0.dp,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEach { (destination, _, icon) ->

                val selected = destination == currentDestination

                val interactionSource = remember { MutableInteractionSource() }

                val isPressed by interactionSource.collectIsPressedAsState()

                val iconTint = when {
                    isPressed -> MaterialTheme.colorScheme.onSurface
                    selected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(48.dp)
                        .height(96.dp)
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            onItemClick(destination)
                        }) {

                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(Modifier.height(20.dp))

                    AnimatedVisibility(selected) {

                        Box(
                            Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }
    }
}


data class BottomBarItem(
    val destination: Destination, val label: String, val icon: Int
)