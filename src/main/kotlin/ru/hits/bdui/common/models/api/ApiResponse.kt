package ru.hits.bdui.common.models.api

sealed interface ApiResponse<out T> {
    companion object {
        fun <T> success(data: T): Success<T> =
            Success(data)

        fun error(errors: List<ErrorContentRaw>): Error =
            Error(errors)

        fun error(error: ErrorContentRaw): Error =
            error(listOf(error))
    }

    data class Success<T>(val data: T) : ApiResponse<T> {
        val type: String = "success"
    }

    data class Error(val errors: List<ErrorContentRaw>) : ApiResponse<Nothing> {
        val type: String = "error"
    }
}