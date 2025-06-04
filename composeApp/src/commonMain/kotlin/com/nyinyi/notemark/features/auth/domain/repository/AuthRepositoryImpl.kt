package com.nyinyi.notemark.features.auth.domain.repository

import MPLog
import com.nyinyi.notemark.core.network.api.NoteMarkApiService
import com.nyinyi.notemark.core.network.model.LoginRequest
import com.nyinyi.notemark.core.network.model.RegisterRequest
import com.nyinyi.notemark.core.network.utils.Resource
import com.nyinyi.notemark.features.auth.domain.model.AuthResult
import com.nyinyi.notemark.features.auth.domain.model.UserLoginData
import com.nyinyi.notemark.features.auth.domain.model.UserRegistrationData
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerializationException

class AuthRepositoryImpl(
    private val apiService: NoteMarkApiService,
) : AuthRepository {
    override suspend fun register(registrationData: UserRegistrationData): Resource<Unit, AuthDomainError> =
        try {
            val response =
                apiService.registerUser(
                    RegisterRequest(
                        username = registrationData.username,
                        email = registrationData.email,
                        password = registrationData.password,
                    ),
                )

            when (response.status) {
                HttpStatusCode.OK, HttpStatusCode.Created -> {
                    Resource.Success(Unit)
                }

                else -> {
                    MPLog
                        .tag("AuthRepositoryImpl")
                        .w("Registration returned unexpected status: ${response.status.value}")
                    Resource.Error(
                        mapHttpClientErrorToDomainError(
                            ClientRequestException(
                                response = response,
                                cachedResponseText = response.bodyAsText(),
                            ),
                            response.status,
                        ),
                    )
                }
            }
        } catch (e: ClientRequestException) {
            Resource.Error(mapHttpClientErrorToDomainError(e, e.response.status))
        } catch (e: ServerResponseException) {
            Resource.Error(AuthDomainError.NetworkError)
        } catch (e: SerializationException) {
            Resource.Error(AuthDomainError.UnknownError("Data parsing error: ${e.message}"))
        } catch (e: Exception) {
            MPLog.tag("AuthRepositoryImpl").e(e.message ?: "Unknown error occurred. ${e.cause}")
            Resource.Error(AuthDomainError.NetworkError)
        }

    override suspend fun login(loginData: UserLoginData): Resource<AuthResult, AuthDomainError> =
        try {
            val response =
                apiService.login(
                    LoginRequest(
                        email = loginData.email,
                        password = loginData.password,
                    ),
                )

            Resource.Success(AuthResult(response.accessToken, response.refreshToken))
        } catch (e: ClientRequestException) {
            Resource.Error(mapHttpClientErrorToDomainError(e, e.response.status))
        } catch (e: ServerResponseException) {
            Resource.Error(AuthDomainError.NetworkError)
        } catch (e: SerializationException) {
            Resource.Error(AuthDomainError.UnknownError("Data parsing error: ${e.message}"))
        } catch (e: Exception) {
            Resource.Error(AuthDomainError.NetworkError)
        }

    private suspend fun mapHttpClientErrorToDomainError(
        e: ClientRequestException,
        statusCode: HttpStatusCode,
    ): AuthDomainError =
        when (statusCode) {
            HttpStatusCode.Conflict -> AuthDomainError.EmailAlreadyExistsError
            HttpStatusCode.Unauthorized -> AuthDomainError.InvalidCredentialsError
            HttpStatusCode.BadRequest -> {
                AuthDomainError.BadRequestError
            }

            HttpStatusCode.TooManyRequests -> AuthDomainError.TooManyRequestsError
            else -> AuthDomainError.UnknownError("HTTP Error: ${statusCode.value}. ${e.message}")
        }
}
