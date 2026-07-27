package com.axelestrada.dinaco.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isLoggedIn: Flow<Boolean>
    suspend fun login()
    suspend fun logout()
}
