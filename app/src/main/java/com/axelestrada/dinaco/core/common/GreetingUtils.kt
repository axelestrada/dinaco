package com.axelestrada.dinaco.core.common

import android.os.Build
import java.time.LocalDateTime

/**
 * Dado un número de horas (0-23), retorna el saludo adecuado.
 */
fun getGreetingForHour(hour: Int): String {
    return when (hour) {
        in 0..11 -> "Buenos días"
        in 12..18 -> "Buenas tardes"
        else -> "Buenas noches"
    }
}

/**
 * Obtiene la hora actual del sistema considerando la versión de Android.
 */
fun getCurrentGreeting(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val hour = LocalDateTime.now().hour
        getGreetingForHour(hour)
    } else {
        "Buenos días"
    }
}