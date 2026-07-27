package com.axelestrada.dinaco.data.repository

import com.axelestrada.dinaco.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl : AuthRepository {
    private val _isLoggedIn = MutableStateFlow(false)
    override val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    override suspend fun login() {
        _isLoggedIn.value = true
    }

    override suspend fun logout() {
        _isLoggedIn.value = false
    }
}
