package ru.hits.bdui.api.external

sealed class ExternalApiCallerException(
    override val message: String?,
    override val cause: Throwable?
) : Exception(message, cause) {
    class TimeoutException(message: String) : ExternalApiCallerException(message, null)
    class UnexpectedException(cause: Throwable) : ExternalApiCallerException(null, cause)
}