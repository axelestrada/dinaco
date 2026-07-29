package com.axelestrada.dinaco.core.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.axelestrada.dinaco.features.login.presentation.screen.LoginScreen
import com.axelestrada.dinaco.features.main.presentation.screen.MainScreen
import com.axelestrada.dinaco.features.login.presentation.viewmodel.AuthViewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel = viewModel()) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    
    val backStack = remember(isLoggedIn) {
        if (isLoggedIn) {
            mutableStateListOf<Destination>(Destination.Main)
        } else {
            mutableStateListOf<Destination>(Destination.Login)
        }
    }

    NavDisplay(backStack = backStack) { key ->
        when (key) {
            Destination.Login -> NavEntry(key) {
                LoginScreen { authViewModel.login() }
            }
            Destination.Main -> NavEntry(key) {
                MainScreen()
            }
            else -> NavEntry(key) { }
        }
    }
}
