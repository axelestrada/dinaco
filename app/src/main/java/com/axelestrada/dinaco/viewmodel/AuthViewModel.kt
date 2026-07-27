package com.axelestrada.dinaco.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axelestrada.dinaco.data.repository.AuthRepositoryImpl
import com.axelestrada.dinaco.domain.repository.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {
    val isLoggedIn = repository.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun login() {
        viewModelScope.launch {
            repository.login()
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
