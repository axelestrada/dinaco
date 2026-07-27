package com.axelestrada.dinaco.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.axelestrada.dinaco.navigation.Destination

@Composable
fun MainScreen() {
    val backStack = remember { mutableStateListOf<Destination>(Destination.Home) }
    val currentDestination = backStack.last()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Triple(Destination.Home, "Inicio", Icons.Default.Home),
                    Triple(Destination.Statistics, "Estadísticas", Icons.Default.BarChart),
                    Triple(Destination.Devices, "Dispositivos", Icons.Default.Devices),
                    Triple(Destination.Settings, "Ajustes", Icons.Default.Settings)
                )
                items.forEach { (dest, label, icon) ->
                    NavigationBarItem(
                        selected = currentDestination == dest,
                        onClick = {
                            if (currentDestination != dest) {
                                // Simple navigation: replace the top of the stack
                                backStack.clear()
                                backStack.add(dest)
                            }
                        },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier.padding(padding)
        ) { key ->
            when (key) {
                Destination.Home -> NavEntry(key) { HomeScreen() }
                Destination.Statistics -> NavEntry(key) { StatisticsScreen() }
                Destination.Devices -> NavEntry(key) { DevicesScreen() }
                Destination.Settings -> NavEntry(key) { SettingsScreen() }
                else -> NavEntry(key) { Text("Unknown") }
            }
        }
    }
}
