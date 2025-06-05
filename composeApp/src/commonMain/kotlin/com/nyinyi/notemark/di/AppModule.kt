package com.nyinyi.notemark.di

import com.nyinyi.notemark.core.network.api.NoteMarkApiService
import com.nyinyi.notemark.core.network.api.NoteMarkApiServiceImpl
import com.nyinyi.notemark.features.auth.domain.repository.AuthRepository
import com.nyinyi.notemark.features.auth.domain.repository.AuthRepositoryImpl
import com.nyinyi.notemark.features.auth.domain.usecase.LoginUseCase
import com.nyinyi.notemark.features.auth.domain.usecase.RegisterUseCase
import com.nyinyi.notemark.features.auth.presentation.login.LoginViewModel
import com.nyinyi.notemark.features.auth.presentation.register.RegisterViewModel
import com.nyinyi.notemark.features.auth.presentation.register.createViewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MOBILE_DEV_CAMPUS_EMAIL = "nyinyizaw.dev@gmail.com"
const val NAMED_USER_EMAIL = "UserEmail"

val appModule =
    module {

        single(named(NAMED_USER_EMAIL)) { MOBILE_DEV_CAMPUS_EMAIL }
        factory<CoroutineScope>(named("ViewModelScope")) { createViewModelScope() }

        single {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            ignoreUnknownKeys = true
                            isLenient = true
                        },
                    )
                }
                install(HttpTimeout) {
                    socketTimeoutMillis = 20_000L
                    requestTimeoutMillis = 20_000L
                }
                install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                print(message)
                            }
                        }
                    level = LogLevel.ALL
                }
                defaultRequest {
                    contentType(ContentType.Application.Json)
                }
            }
        }

        single<NoteMarkApiService> {
            NoteMarkApiServiceImpl(
                httpClient = get(),
                mobileDevCampusEmail = get(named(NAMED_USER_EMAIL)),
            )
        }

        single<AuthRepository> {
            AuthRepositoryImpl(
                apiService = get(),
            )
        }

        factory { RegisterUseCase(authRepository = get()) }
        factory { LoginUseCase(authRepository = get()) }

        viewModel {
            RegisterViewModel(
                registerUseCase = get(),
                coroutineScopeProvider = get(named("ViewModelScope")),
            )
        }

        viewModel {
            LoginViewModel(
                loginUseCase = get(),
                coroutineScopeProvider = get(named("ViewModelScope")),
            )
        }
    }
