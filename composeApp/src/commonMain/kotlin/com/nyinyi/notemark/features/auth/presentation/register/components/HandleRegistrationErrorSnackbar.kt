package com.nyinyi.notemark.features.auth.presentation.register.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.nyinyi.notemark.features.auth.domain.repository.AuthDomainError

@Composable fun HandleRegistrationErrorSnackbar(
    registrationError: AuthDomainError?,
    snackbarHostState: SnackbarHostState,
    onConsumeError: () -> Unit,
) {
    LaunchedEffect(key1 = registrationError) {
        registrationError?.let { error ->
            val errorMessage =
                when (error) {
                    AuthDomainError.EmailAlreadyExistsError -> "A user with that email or username already exists."
                    AuthDomainError.InvalidCredentialsError -> "Invalid credentials. Please check your input."
                    AuthDomainError.NetworkError -> "Network error. Please check connection and try again."
                    AuthDomainError.BadRequestError -> "Invalid data. Please check all fields."
                    AuthDomainError.TooManyRequestsError -> "Too many requests. Please try again later."
                    is AuthDomainError.UnknownError -> error.message ?: "An unknown error occurred."
                }
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short,
            )
            onConsumeError()
        }
    }
}
