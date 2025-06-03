package com.nyinyi.notemark.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object SplashScreen : Screens

    @Serializable
    data object LandingScreen : Screens
}
