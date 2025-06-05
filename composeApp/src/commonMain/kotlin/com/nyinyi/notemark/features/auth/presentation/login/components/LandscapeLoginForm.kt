package com.nyinyi.notemark.features.auth.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nyinyi.notemark.features.auth.presentation.login.LoginState
import com.nyinyi.notemark.features.auth.presentation.login.LoginViewModel
import com.nyinyi.notemark.features.auth.presentation.register.components.EmailInputField
import com.nyinyi.notemark.features.auth.presentation.register.components.PasswordInputField

@Composable
fun LandscapeLoginForm(
    modifier: Modifier = Modifier,
    state: LoginState,
    viewModel: LoginViewModel,
    focusManager: FocusManager,
    onNavigateToRegister: () -> Unit,
) {
    Row(
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
        ) {
            LoginHeader()
        }
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
        ) {
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
}
