package com.nyinyi.notemark.core.network.utils

sealed class Resource<out D, out E> {
    data class Success<out D>(
        val data: D,
    ) : Resource<D, Nothing>()

    data class Error<out E>(
        val error: E,
    ) : Resource<Nothing, E>()
}
