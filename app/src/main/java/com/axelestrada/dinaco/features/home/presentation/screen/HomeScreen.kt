package com.axelestrada.dinaco.features.home.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.R
import com.axelestrada.dinaco.core.common.getCurrentGreeting
import com.axelestrada.dinaco.core.designsystem.components.Header
import com.axelestrada.dinaco.core.designsystem.components.StatusBadge
import com.axelestrada.dinaco.core.designsystem.theme.Typography

@Composable
fun HomeScreen() {
    val greeting = getCurrentGreeting()
    var tankPercentage by remember { mutableFloatStateOf(0.82f) }

    Column(
        modifier = Modifier.padding(24.dp)
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
            horizontalAlignment = Alignment.CenterHorizontally
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Slider(
                    value = tankPercentage,
                    onValueChange = { tankPercentage = it },
                    valueRange = 0f..1f,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "${(tankPercentage * 100).toInt()}%",
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
