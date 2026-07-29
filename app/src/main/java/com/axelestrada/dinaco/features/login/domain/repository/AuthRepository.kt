package com.axelestrada.dinaco.features.login.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isLoggedIn: Flow<Boolean>
    suspend fun login()
    suspend fun logout()
}
