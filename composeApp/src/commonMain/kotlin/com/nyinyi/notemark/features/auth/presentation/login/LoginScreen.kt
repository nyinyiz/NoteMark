package com.nyinyi.notemark.features.auth.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.nyinyi.notemark.core.utils.ScreenConfiguration
import com.nyinyi.notemark.core.utils.determineScreenConfiguration
import com.nyinyi.notemark.features.auth.domain.repository.AuthDomainError
import com.nyinyi.notemark.features.auth.presentation.login.components.LandscapeLoginForm
import com.nyinyi.notemark.features.auth.presentation.login.components.LoginButton
import com.nyinyi.notemark.features.auth.presentation.login.components.LoginHeader
import com.nyinyi.notemark.features.auth.presentation.login.components.LoginNavigationRow
import com.nyinyi.notemark.features.auth.presentation.login.components.LoginSuccessDialog
import com.nyinyi.notemark.features.auth.presentation.register.components.EmailInputField
import com.nyinyi.notemark.features.auth.presentation.register.components.PasswordInputField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    val configuration = determineScreenConfiguration(windowSizeClass)
    val focusManager = LocalFocusManager.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    state.loginSuccessData?.let { authResult ->
        LoginSuccessDialog(
            authResult = authResult,
            onDismiss = {
                onLoginSuccess()
                viewModel.consumeLoginSuccess()
            },
        )
    }

    LaunchedEffect(key1 = state.loginError) {
        state.loginError?.let { error ->
            val errorMessage =
                when (error) {
                    AuthDomainError.InvalidCredentialsError -> "Invalid email/username or password."
                    AuthDomainError.NetworkError -> "Network error. Please check your connection."
                    is AuthDomainError.UnknownError -> error.message ?: "An unknown error occurred."
                    else -> "An unexpected error occurred."
                }
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short,
            )
            viewModel.consumeLoginError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.primary,
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            ) {
                when (configuration) {
                    is ScreenConfiguration.MobilePortrait -> {
                        LoginForm(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                            state = state,
                            viewModel = viewModel,
                            focusManager = focusManager,
                            onNavigateToRegister = onNavigateToRegister,
                        )
                    }

                    is ScreenConfiguration.MobileLandscape -> {
                        LandscapeLoginForm(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                            state = state,
                            viewModel = viewModel,
                            focusManager = focusManager,
                            onNavigateToRegister = onNavigateToRegister,
                        )
                    }

                    is ScreenConfiguration.TabletPortrait -> {
                        LoginForm(
                            modifier =
                                Modifier
                                    .width(560.dp)
                                    .padding(top = 56.dp)
                                    .align(Alignment.CenterHorizontally),
                            state = state,
                            viewModel = viewModel,
                            focusManager = focusManager,
                            onNavigateToRegister = onNavigateToRegister,
                        )
                    }

                    is ScreenConfiguration.TabletLandscape -> {
                    }

                    ScreenConfiguration.Undefined -> {
                    }
                }
            }
        }
    }
}

@Composable
private fun LoginForm(
    modifier: Modifier = Modifier,
    state: LoginState,
    viewModel: LoginViewModel,
    focusManager: FocusManager,
    onNavigateToRegister: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        LoginHeader()

        Spacer(modifier = Modifier.height(32.dp))

        EmailInputField(
            value = state.emailOrUsernameInput,
            onValueChange = { viewModel.onEmailOrUsernameChanged(it) },
            error = state.emailOrUsernameError,
            focusManager = focusManager,
        )

        PasswordInputField(
            value = state.passwordInput,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = "Password",
            placeholder = "Password",
            isVisible = state.isPasswordVisible,
            onVisibilityToggle = { viewModel.onTogglePasswordVisibility() },
            error = state.passwordError,
            focusManager = focusManager,
            imeAction = ImeAction.Done,
            onDoneAction = {
                focusManager.clearFocus()
                if (viewModel.areAllInputsFilled()) {
                    viewModel.onLoginClicked()
                }
            },
        )
        Spacer(modifier = Modifier.height(24.dp))

        LoginButton(
            isLoading = state.isLoading,
            isEnabled = viewModel.areAllInputsFilled(),
            onClick = {
                focusManager.clearFocus()
                viewModel.onLoginClicked()
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        LoginNavigationRow(onNavigateToRegister = onNavigateToRegister)
    }
}
