package com.axelestrada.dinaco.features.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
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

    // Estados para el Slider flotante
    var sliderOffset by remember { mutableStateOf(Offset(200f, 400f)) }
    var isSliderVisible by remember { mutableStateOf(true) }
    var isFabVisible by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
        ) {
            Header(
                title = "Hola, Axel", subtitle = greeting, showNotifications = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    StatusBadge(text = "Tinaco Principal")

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
                        StatCard(label = "VOLUMEN ACTUAL", value = "984", unit = "Lts")
                        StatCard(label = "AUTONOMÍA", value = "~4", unit = "días")
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
                    .height(48.dp), contentAlignment = Alignment.Center) {
                Slider(
                    value = tankPercentage,
                    onValueChange = { tankPercentage = it },
                    valueRange = 0f..1f,
                    modifier = Modifier
                        .width(180.dp) // El "ancho" se convierte en la altura al rotar
                )
            }
        }

        // Botón para volver a mostrar el slider si se ocultó
        if (isFabVisible) {
            FloatingActionButton(
                onClick = {
                    isSliderVisible = true
                    isFabVisible = false
                    sliderOffset = Offset(200f, 400f) // Reset position
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Mostrar control")
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, unit: String) {
    Column {
        Text(
            text = label,
            style = ExtendedTypography.Overline,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            style = Typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
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
