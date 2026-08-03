package com.axelestrada.dinaco.features.home.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.R
import com.axelestrada.dinaco.core.common.getCurrentGreeting
import com.axelestrada.dinaco.core.designsystem.components.Header
import com.axelestrada.dinaco.core.designsystem.components.StatusBadge
import com.axelestrada.dinaco.core.designsystem.theme.ExtendedTypography
import com.axelestrada.dinaco.core.designsystem.theme.Typography
import kotlin.math.roundToInt

@Composable
fun HomeScreen() {
    val greeting = getCurrentGreeting()
    var tankPercentage by remember { mutableFloatStateOf(0.82f) }

    val scrollState = rememberScrollState()

    // Estados para el Slider flotante
    var sliderOffset by remember { mutableStateOf(Offset(200f, 400f)) }
    var isSliderVisible by remember { mutableStateOf(true) }
    var isFabVisible by remember { mutableStateOf(false) }

    val screenHeightPx = LocalWindowInfo.current.containerSize.height.toFloat()

    val targetColor = when (tankPercentage) {
        in 0.4f..1f -> MaterialTheme.colorScheme.primary
        in 0.2f..0.4f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    val statColor = when (tankPercentage) {
        in 0.4f..1f -> MaterialTheme.colorScheme.onBackground
        in 0.2f..0.4f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    val animatedStatColor by animateColorAsState(
        targetValue = statColor,
        animationSpec = tween(durationMillis = 600, easing = EaseOut),
        label = "statColorTransition"
    )

    val color by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 600, easing = EaseOut),
        label = "colorTransition"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val constraints = this
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = constraints.maxHeight - 48.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Header(
                            title = "Hola, Axel", subtitle = greeting, showNotifications = true
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            StatusBadge(text = "Tinaco Principal", badgeColor = color)

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_lucide_clock_4),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(10.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    "hace 2 min",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = Typography.labelSmall
                                )
                            }
                        }
                    }

                    WaterTankGauge(
                        percentage = tankPercentage
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            StatCard(
                                label = "VOLUMEN ACTUAL",
                                value = "984",
                                unit = "Lts",
                                color = animatedStatColor
                            )
                            StatCard(
                                label = "AUTONOMÍA",
                                value = "4",
                                unit = "días",
                                color = animatedStatColor
                            )
                        }
                    }
                }
            }
        }

        // Slider Flotante y Vertical
        if (isSliderVisible) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            sliderOffset.x.roundToInt(), sliderOffset.y.roundToInt()
                        )
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                // Si se arrastra cerca del fondo (ej. último 15% de la pantalla), se oculta
                                if (sliderOffset.y > screenHeightPx * 0.85f) {
                                    isSliderVisible = false
                                    isFabVisible = true
                                }
                            }) { change, dragAmount ->
                            change.consume()
                            sliderOffset = Offset(
                                x = sliderOffset.x + dragAmount.x, y = sliderOffset.y + dragAmount.y
                            )
                        }
                    }
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 4.dp)
                    .width(220.dp)
                    .height(48.dp),
                contentAlignment = Alignment.Center) {
                Slider(
                    value = tankPercentage,
                    onValueChange = { tankPercentage = it },
                    valueRange = 0f..1f,
                    modifier = Modifier.width(180.dp),
                    colors = SliderDefaults.colors().copy(
                        thumbColor = color,
                        activeTrackColor = color
                    )// El "ancho" se convierte en la altura al rotar
                )
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, unit: String, color: Color) {
    Column {
        Text(
            text = label,
            style = ExtendedTypography.Overline,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            style = Typography.headlineMedium,
            color = color,
            text = buildAnnotatedString {
                append(value)

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = Typography.bodySmall.fontFamily,
                        fontWeight = Typography.bodySmall.fontWeight,
                        fontSize = Typography.bodySmall.fontSize,
                    )
                ) {
                    append(" $unit")
                }
            })

    }
}
