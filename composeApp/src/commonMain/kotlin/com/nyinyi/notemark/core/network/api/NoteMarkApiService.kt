package com.nyinyi.notemark.core.network.api

import com.nyinyi.notemark.core.network.model.LoginRequest
import com.nyinyi.notemark.core.network.model.LoginResponse
import com.nyinyi.notemark.core.network.model.RefreshTokenRequest
import com.nyinyi.notemark.core.network.model.RegisterRequest
import com.nyinyi.notemark.core.network.utils.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface NoteMarkApiService {
    suspend fun registerUser(registerRequest: RegisterRequest)

    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): LoginResponse
}

class NoteMarkApiServiceImpl(
    private val httpClient: HttpClient,
    private val mobileDevCampusEmail: String,
) : NoteMarkApiService {
    override suspend fun registerUser(registerRequest: RegisterRequest) {
        httpClient.post("${NetworkConstants.BASE_URL}${NetworkConstants.REGISTER_ENDPOINT}") {
            contentType(ContentType.Application.Json)
            header(NetworkConstants.HEADER_X_USER_EMAIL, mobileDevCampusEmail)
            setBody(registerRequest)
        }
    }

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        httpClient
            .post("${NetworkConstants.BASE_URL}${NetworkConstants.LOGIN_ENDPOINT}") {
                contentType(ContentType.Application.Json)
                header(NetworkConstants.HEADER_X_USER_EMAIL, mobileDevCampusEmail)
                setBody(loginRequest)
            }.body()

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): LoginResponse =
        httpClient
            .post("${NetworkConstants.BASE_URL}${NetworkConstants.REFRESH_ENDPOINT}") {
                contentType(ContentType.Application.Json)
                header(NetworkConstants.HEADER_X_USER_EMAIL, mobileDevCampusEmail)
                setBody(refreshTokenRequest)
            }.body()
}
