package com.axelestrada.dinaco.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.axelestrada.dinaco.R
import com.axelestrada.dinaco.navigation.Destination
import com.axelestrada.dinaco.ui.components.BottomBarItem
import com.axelestrada.dinaco.ui.components.GlassBottomBar

@Composable
fun MainScreen() {
    val backStack = remember { mutableStateListOf<Destination>(Destination.Home) }
    val currentDestination = backStack.last()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.padding(0.dp),
        bottomBar = {
            GlassBottomBar(
                currentDestination = currentDestination, items = listOf(
                    BottomBarItem(Destination.Home, "Home", R.drawable.ic_lucide_house),
                    BottomBarItem(
                        Destination.Statistics, "Statistics", R.drawable.ic_lucide_activity
                    ),
                    BottomBarItem(Destination.Devices, "Devices", R.drawable.ic_lucide_layout_grid),
                    BottomBarItem(Destination.Settings, "Settings", R.drawable.ic_lucide_settings),
                ), onItemClick = { destination ->
                    if (currentDestination != destination) {
                        backStack.clear()
                        backStack.add(destination)
                    }
                })
        }) { padding ->
        NavDisplay(
            backStack = backStack, modifier = Modifier.padding(padding)
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
