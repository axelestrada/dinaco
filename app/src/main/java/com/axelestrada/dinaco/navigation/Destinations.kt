package com.axelestrada.dinaco.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Login : Destination

    @Serializable
    data object Main : Destination

    @Serializable
    data object Home : Destination

    @Serializable
    data object Statistics : Destination

    @Serializable
    data object Devices : Destination

    @Serializable
    data object Settings : Destination
}
