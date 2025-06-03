package com.nyinyi.notemark.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.nyinyi.notemark.features.auth.LandingScreen
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
            LandingScreen()
        }
    }
}
