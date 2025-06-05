package com.nyinyi.notemark.features.auth.presentation.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.nyinyi.notemark.core.utils.ScreenConfiguration
import com.nyinyi.notemark.core.utils.determineScreenConfiguration
import com.nyinyi.notemark.features.auth.presentation.register.components.HandleRegistrationErrorSnackbar
import com.nyinyi.notemark.features.auth.presentation.register.components.LandscapeRegisterFormContent
import com.nyinyi.notemark.features.auth.presentation.register.components.RegisterFormContent
import com.nyinyi.notemark.features.auth.presentation.register.components.RegistrationSuccessDialog
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    viewModel: RegisterViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onRegistrationSuccess: () -> Unit,
) {
    val configuration = determineScreenConfiguration(windowSizeClass)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    if (state.registrationSuccess) {
        RegistrationSuccessDialog(
            onDismiss = {
                viewModel.consumeRegistrationSuccess()
                onRegistrationSuccess()
            },
        )
    }

    HandleRegistrationErrorSnackbar(
        registrationError = state.registrationError,
        snackbarHostState = snackbarHostState,
        onConsumeError = { viewModel.consumeRegistrationError() },
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.primary,
    ) { paddingValues ->
        Card(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            shape =
                RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp,
                ),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            when (configuration) {
                is ScreenConfiguration.MobilePortrait -> {
                    RegisterFormContent(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToLogin = onNavigateToLogin,
                    )
                }

                is ScreenConfiguration.MobileLandscape -> {
                    LandscapeRegisterFormContent(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToLogin = onNavigateToLogin,
                    )
                }

                is ScreenConfiguration.TabletPortrait -> {
                    RegisterFormContent(
                        modifier =
                            Modifier
                                .width(560.dp)
                                .padding(top = 56.dp)
                                .align(Alignment.CenterHorizontally),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToLogin = onNavigateToLogin,
                    )
                }

                is ScreenConfiguration.TabletLandscape -> {
                    LandscapeRegisterFormContent(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                        state = state,
                        viewModel = viewModel,
                        onNavigateToLogin = onNavigateToLogin,
                    )
                }

                ScreenConfiguration.Undefined -> {
                }
            }
        }
    }
}
