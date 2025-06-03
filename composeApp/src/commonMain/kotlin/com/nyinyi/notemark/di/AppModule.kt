package com.nyinyi.notemark.di

import com.nyinyi.notemark.core.network.api.NoteMarkApiService
import com.nyinyi.notemark.core.network.api.NoteMarkApiServiceImpl
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
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MOBILE_DEV_CAMPUS_EMAIL = "nyinyizaw.dev@gmail.com"
const val NAMED_USER_EMAIL = "UserEmail"
const val NAMED_DEBUG_MODE = "DebugMode"

val appModule =
    module {

        single(named(NAMED_USER_EMAIL)) { MOBILE_DEV_CAMPUS_EMAIL }

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
    }
