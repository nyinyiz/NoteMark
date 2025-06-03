package com.nyinyi.notemark.features.auth.domain.usecase

import com.nyinyi.notemark.core.network.utils.Resource
import com.nyinyi.notemark.features.auth.domain.model.UserRegistrationData
import com.nyinyi.notemark.features.auth.domain.repository.AuthDomainError
import com.nyinyi.notemark.features.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(registrationData: UserRegistrationData): Resource<Unit, AuthDomainError> {
        if (registrationData.username.length < 3) {
            return Resource.Error(AuthDomainError.BadRequestError)
        }
        if (!registrationData.email.contains("@")) {
            return Resource.Error(AuthDomainError.BadRequestError)
        }
        if (registrationData.password.length < 6) {
            return Resource.Error(AuthDomainError.BadRequestError)
        }

        return authRepository.register(registrationData)
    }
}
