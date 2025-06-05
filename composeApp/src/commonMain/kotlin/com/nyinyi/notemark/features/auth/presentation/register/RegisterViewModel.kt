package com.nyinyi.notemark.features.auth.presentation.register

import androidx.lifecycle.ViewModel
import com.nyinyi.notemark.core.network.utils.Resource
import com.nyinyi.notemark.features.auth.domain.model.UserRegistrationData
import com.nyinyi.notemark.features.auth.domain.repository.AuthDomainError
import com.nyinyi.notemark.features.auth.domain.usecase.RegisterUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val coroutineScopeProvider: CoroutineScope,
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun areAllInputsFilled(): Boolean {
        val currentState = _state.value
        return currentState.usernameInput.isNotBlank() &&
            currentState.emailInput.isNotBlank() &&
            currentState.passwordInput.isNotBlank() &&
            currentState.repeatPasswordInput.isNotBlank()
    }

    fun onUsernameChanged(value: String) {
        _state.update {
            it.copy(
                usernameInput = value,
                usernameError = null,
                registrationError = null,
            )
        }
    }

    fun onEmailChanged(value: String) {
        _state.update { it.copy(emailInput = value, emailError = null, registrationError = null) }
    }

    fun onPasswordChanged(value: String) {
        _state.update {
            it.copy(
                passwordInput = value,
                passwordError = null,
                registrationError = null,
            )
        }
    }

    fun onRepeatPasswordChanged(value: String) {
        _state.update {
            it.copy(
                repeatPasswordInput = value,
                repeatPasswordError = null,
                registrationError = null,
            )
        }
    }

    fun onTogglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onToggleRepeatPasswordVisibility() {
        _state.update { it.copy(isRepeatPasswordVisible = !it.isRepeatPasswordVisible) }
    }

    fun onRegisterClicked() {
        performRegistration()
    }

    fun consumeRegistrationError() {
        _state.update { it.copy(registrationError = null) }
    }

    fun consumeRegistrationSuccess() {
        _state.update { it.copy(registrationSuccess = false) }
    }

    private fun performRegistration() {
        if (!validateInputs()) return

        _state.update { it.copy(isLoading = true, registrationError = null) }

        coroutineScopeProvider.launch {
            val result =
                registerUseCase(
                    UserRegistrationData(
                        username = _state.value.usernameInput.trim(),
                        email = _state.value.emailInput.trim(),
                        password = _state.value.passwordInput,
                    ),
                )

            when (result) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false, registrationSuccess = true) }
                }

                is Resource.Error -> {
                    _state.update { it.copy(isLoading = false, registrationError = result.error) }
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        val currentState = _state.value

        val username = currentState.usernameInput.trim()
        if (username.length < 3) {
            _state.update { it.copy(usernameError = "Username must be at least 3 characters.") }
            isValid = false
        } else if (username.length > 20) {
            _state.update { it.copy(usernameError = "Username must be less than 20 characters.") }
            isValid = false
        } else {
            _state.update { it.copy(usernameError = null) }
        }

        val email = currentState.emailInput.trim()

        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        if (!emailRegex.matches(email)) {
            _state.update { it.copy(emailError = "Please provide a valid email address.") }
            isValid = false
        } else {
            _state.update { it.copy(emailError = null) }
        }

        val password = currentState.passwordInput
        val passwordRegex =
            Regex("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~]).{8,}\$")
        if (password.length < 8) {
            _state.update { it.copy(passwordError = "Password must be at least 8 characters and include a number or symbol") }
            isValid = false
        } else if (!passwordRegex.matches(password)) {
            _state.update { it.copy(passwordError = "Password needs letters, numbers, and a symbol.") }
            isValid = false
        } else {
            _state.update { it.copy(passwordError = null) }
        }

        if (password != currentState.repeatPasswordInput) {
            _state.update { it.copy(repeatPasswordError = "Passwords do not match.") }
            isValid = false
        } else {
            _state.update { it.copy(repeatPasswordError = null) }
        }

        return isValid
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScopeProvider.cancel()
    }
}

data class RegisterState(
    val usernameInput: String = "",
    val emailInput: String = "",
    val passwordInput: String = "",
    val repeatPasswordInput: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val repeatPasswordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isRepeatPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val registrationError: AuthDomainError? = null,
    val registrationSuccess: Boolean = false,
)

fun createViewModelScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
