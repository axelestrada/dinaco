package com.axelestrada.dinaco.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.axelestrada.dinaco.core.common.snackbar.SnackbarManager
import com.axelestrada.dinaco.core.designsystem.components.FloatingToast
import com.axelestrada.dinaco.features.login.presentation.screen.LoginScreen
import com.axelestrada.dinaco.features.login.presentation.viewmodel.AuthViewModel
import com.axelestrada.dinaco.features.main.presentation.screen.MainScreen

@Composable
fun AppNavigation(authViewModel: AuthViewModel = viewModel()) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    var snackbarMessage by remember { mutableStateOf<String?>(null) }
    var showSnackbar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        SnackbarManager.messages.collect { message ->
            snackbarMessage = message
            showSnackbar = true
        }
    }

    val backStack = remember(isLoggedIn) {
        if (isLoggedIn) {
            mutableStateListOf<Destination>(Destination.Main)
        } else {
            mutableStateListOf<Destination>(Destination.Login)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

        FloatingToast(
            message = snackbarMessage ?: "",
            visible = showSnackbar,
            onDismiss = { showSnackbar = false },
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
