package com.annti.movieapp.domain

class AppError(
    val code: Code,
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause) {

    enum class Code {
        SERVER_ERROR,
        TIMEOUT,
        NETWORK_CONNECTION
    }
}