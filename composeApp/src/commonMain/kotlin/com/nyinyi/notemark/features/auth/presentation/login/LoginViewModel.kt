package com.nyinyi.notemark.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import com.nyinyi.notemark.core.network.utils.Resource
import com.nyinyi.notemark.features.auth.domain.model.AuthResult
import com.nyinyi.notemark.features.auth.domain.model.UserLoginData
import com.nyinyi.notemark.features.auth.domain.repository.AuthDomainError
import com.nyinyi.notemark.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val coroutineScopeProvider: CoroutineScope,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun areAllInputsFilled(): Boolean {
        val currentState = _state.value
        return currentState.emailOrUsernameInput.isNotBlank() &&
            currentState.passwordInput.isNotBlank()
    }

    fun onEmailOrUsernameChanged(value: String) {
        _state.update {
            it.copy(
                emailOrUsernameInput = value,
                emailOrUsernameError = null,
                loginError = null,
            )
        }
    }

    fun onPasswordChanged(value: String) {
        _state.update {
            it.copy(
                passwordInput = value,
                passwordError = null,
                loginError = null,
            )
        }
    }

    fun onTogglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClicked() {
        performLogin()
    }

    fun consumeLoginError() {
        _state.update { it.copy(loginError = null) }
    }

    fun consumeLoginSuccess() {
        _state.update { it.copy(loginSuccessData = null) }
    }

    private fun performLogin() {
        if (!validateInputs()) return

        _state.update { it.copy(isLoading = true, loginError = null) }

        coroutineScopeProvider.launch {
            val result =
                loginUseCase(
                    UserLoginData(
                        email = _state.value.emailOrUsernameInput.trim(),
                        password = _state.value.passwordInput,
                    ),
                )

            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            loginSuccessData = result.data,
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, loginError = result.error) }
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        val currentState = _state.value
        if (currentState.emailOrUsernameInput.isBlank()) {
            _state.update { it.copy(emailOrUsernameError = "Email or username cannot be empty.") }
            isValid = false
        } else {
            _state.update { it.copy(emailOrUsernameError = null) }
        }

        if (currentState.passwordInput.isBlank()) {
            _state.update { it.copy(passwordError = "Password cannot be empty.") }
            isValid = false
        } else {
            _state.update { it.copy(passwordError = null) }
        }
        return isValid
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScopeProvider.cancel()
    }
}

data class LoginState(
    val emailOrUsernameInput: String = "",
    val passwordInput: String = "",
    val emailOrUsernameError: String? = null,
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val loginError: AuthDomainError? = null,
    val loginSuccessData: AuthResult? = null,
)
