package com.nyinyi.notemark.features.auth.domain.usecase

import com.nyinyi.notemark.core.network.utils.Resource
import com.nyinyi.notemark.features.auth.domain.model.AuthResult
import com.nyinyi.notemark.features.auth.domain.model.UserLoginData
import com.nyinyi.notemark.features.auth.domain.repository.AuthDomainError
import com.nyinyi.notemark.features.auth.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(loginData: UserLoginData): Resource<AuthResult, AuthDomainError> {
        if (loginData.email.isBlank() || !loginData.email.contains("@")) {
            return Resource.Error(AuthDomainError.BadRequestError)
        }
        if (loginData.password.isBlank()) {
            return Resource.Error(AuthDomainError.BadRequestError)
        }

        val result = authRepository.login(loginData)

        // TODO: save auth tokens
        // if (result is Resource.Success) {
        //     sessionRepository.saveAuthTokens(result.data.accessToken, result.data.refreshToken)
        // }

        return result
    }
}
