package com.nyinyi.notemark.features.auth.presentation.register.components

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nyinyi.notemark.features.auth.presentation.register.RegisterState
import com.nyinyi.notemark.features.auth.presentation.register.RegisterViewModel

@Composable
fun LandscapeRegisterFormContent(
    modifier: Modifier = Modifier,
    state: RegisterState,
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
        ) {
            ScreenHeader()
        }
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
        ) {
            UsernameInputField(
                value = state.usernameInput,
                onValueChange = { viewModel.onUsernameChanged(it) },
                error = state.usernameError,
                focusManager = focusManager,
            )

            EmailInputField(
                value = state.emailInput,
                onValueChange = { viewModel.onEmailChanged(it) },
                error = state.emailError,
                focusManager = focusManager,
            )

            PasswordInputField(
                value = state.passwordInput,
                onValueChange = { viewModel.onPasswordChanged(it) },
                isVisible = state.isPasswordVisible,
                onVisibilityToggle = { viewModel.onTogglePasswordVisibility() },
                error = state.passwordError,
                focusManager = focusManager,
                imeAction = ImeAction.Next,
            )

            PasswordInputField(
                value = state.repeatPasswordInput,
                onValueChange = { viewModel.onRepeatPasswordChanged(it) },
                label = "Repeat password",
                placeholder = "Confirm your password",
                isVisible = state.isRepeatPasswordVisible,
                onVisibilityToggle = { viewModel.onToggleRepeatPasswordVisibility() },
                error = state.repeatPasswordError,
                focusManager = focusManager,
                imeAction = ImeAction.Done,
                onDoneAction = {
                    focusManager.clearFocus()
                    viewModel.onRegisterClicked()
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

            SubmitButton(
                isLoading = state.isLoading,
                enabled = viewModel.areAllInputsFilled(),
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onRegisterClicked()
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

            LoginNavigationLink(onNavigateToLogin = onNavigateToLogin)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
