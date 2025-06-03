package com.nyinyi.notemark.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.nyinyi.notemark.features.auth.presentation.landingscreen.LandingScreen
import com.nyinyi.notemark.features.auth.presentation.register.RegisterScreen
import com.nyinyi.notemark.features.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen,
    ) {
        composable<Screens.SplashScreen> {
            SplashScreen {
                navController.navigate(
                    route = Screens.LandingScreen,
                    navOptions =
                        navOptions {
                            popUpTo(Screens.SplashScreen) {
                                inclusive = true
                            }
                        },
                )
            }
        }
        composable<Screens.LandingScreen> {
            LandingScreen(
                onSignUpClick = {
                    navController.navigate(Screens.RegisterScreen)
                },
                onLoginClick = {
                },
            )
        }

        composable<Screens.RegisterScreen> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screens.LandingScreen) {
                        popUpTo(Screens.LandingScreen) {
                            inclusive = true
                        }
                    }
                },
                onRegistrationSuccess = {
                    navController.navigate(Screens.LandingScreen)
                },
            )
        }
    }
}
