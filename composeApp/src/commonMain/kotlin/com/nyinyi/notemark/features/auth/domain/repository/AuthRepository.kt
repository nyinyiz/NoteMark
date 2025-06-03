package com.nyinyi.notemark.features.auth.domain.repository

import com.nyinyi.notemark.core.network.utils.Resource
import com.nyinyi.notemark.features.auth.domain.model.AuthResult
import com.nyinyi.notemark.features.auth.domain.model.UserLoginData
import com.nyinyi.notemark.features.auth.domain.model.UserRegistrationData

interface AuthRepository {
    suspend fun register(registrationData: UserRegistrationData): Resource<Unit, AuthDomainError>

    suspend fun login(loginData: UserLoginData): Resource<AuthResult, AuthDomainError>
}

sealed interface DomainError

sealed class AuthDomainError : DomainError {
    data object EmailAlreadyExistsError : AuthDomainError() // HTTP 409 on register

    data object InvalidCredentialsError : AuthDomainError() // HTTP 401 on login

    data object NetworkError : AuthDomainError() // General network issues

    data object BadRequestError : AuthDomainError() // HTTP 400, e.g., malformed request

    data object TooManyRequestsError : AuthDomainError() // HTTP 429

    data class UnknownError(
        val message: String?,
    ) : AuthDomainError()
}
