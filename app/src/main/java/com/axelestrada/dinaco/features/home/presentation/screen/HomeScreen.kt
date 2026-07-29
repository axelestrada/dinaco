package com.axelestrada.dinaco.features.home.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.axelestrada.dinaco.core.designsystem.components.Header
import com.axelestrada.dinaco.core.common.getCurrentGreeting

@Composable
fun HomeScreen() {
    val greeting = getCurrentGreeting()

    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Header(
            title = "Hola, Axel", subtitle = greeting, showNotifications = true
        )
    }
}