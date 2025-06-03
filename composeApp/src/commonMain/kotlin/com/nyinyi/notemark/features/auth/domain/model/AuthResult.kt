package com.nyinyi.notemark.features.auth.domain.model

data class AuthResult(
    val accessToken: String,
    val refreshToken: String,
)
