package com.axelestrada.dinaco.ui.screens.main

import androidx.lifecycle.ViewModel
import com.axelestrada.dinaco.navigation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _currentDestination =
        MutableStateFlow<Destination>(Destination.Home)

    val currentDestination =
        _currentDestination.asStateFlow()

    fun navigate(destination: Destination){
        _currentDestination.value = destination
    }
}