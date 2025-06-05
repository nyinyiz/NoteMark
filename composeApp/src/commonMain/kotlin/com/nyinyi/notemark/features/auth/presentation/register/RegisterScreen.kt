package com.nyinyi.notemark.features.auth.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.nyinyi.notemark.features.auth.domain.repository.AuthDomainError
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    viewModel: RegisterViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onRegistrationSuccess: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    if (state.registrationSuccess) {
        AlertDialog(
            onDismissRequest = {
                viewModel.consumeRegistrationSuccess()
                onRegistrationSuccess()
            },
            title = {
                Text(text = "Registration Successful!")
            },
            text = {
                Text(text = "Your account has been created successfully. You can now log in.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.consumeRegistrationSuccess()
                        onRegistrationSuccess()
                    },
                ) {
                    Text("OK")
                }
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        )
    }

    LaunchedEffect(key1 = state.registrationError) {
        state.registrationError?.let { error ->
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
            viewModel.consumeRegistrationError()
        }
    }

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
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Create account",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Text(
                    text = "Capture your thoughts and ideas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 32.dp),
                )

                TextField(
                    value = state.usernameInput,
                    onValueChange = { viewModel.onUsernameChanged(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Username") },
                    placeholder = { Text("John.doe") },
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                    keyboardActions =
                        KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    isError = state.usernameError != null,
                    supportingText = {
                        state.usernameError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = state.emailInput,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    placeholder = { Text("john.doe@example.com") },
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                    keyboardActions =
                        KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    isError = state.emailError != null,
                    supportingText = {
                        state.emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.passwordInput,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password") },
                    singleLine = true,
                    visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
                    keyboardActions =
                        KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (state.isPasswordVisible) "Hide password" else "Show password",
                            )
                        }
                    },
                    isError = state.passwordError != null,
                    supportingText = {
                        val supportText =
                            state.passwordError
                                ?: "Use 8+ characters with letters, numbers & symbols."
                        Text(
                            supportText,
                            color =
                                if (state.passwordError !=
                                    null
                                ) {
                                    MaterialTheme.colorScheme.error
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                        )
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.repeatPasswordInput,
                    onValueChange = { viewModel.onRepeatPasswordChanged(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Repeat password") },
                    placeholder = { Text("Confirm your password") },
                    singleLine = true,
                    visualTransformation = if (state.isRepeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                    keyboardActions =
                        KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            viewModel.onRegisterClicked()
                        }),
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onToggleRepeatPasswordVisibility() }) {
                            Icon(
                                imageVector = if (state.isRepeatPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (state.isRepeatPasswordVisible) "Hide password" else "Show password",
                            )
                        }
                    },
                    isError = state.repeatPasswordError != null,
                    supportingText = {
                        state.repeatPasswordError?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.onRegisterClicked()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White,
                            disabledContainerColor = MaterialTheme.colorScheme.surface,
                            disabledContentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                    enabled = viewModel.areAllInputsFilled(),
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text("Create account", fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.clickable(onClick = onNavigateToLogin),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Already have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        "Login",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
