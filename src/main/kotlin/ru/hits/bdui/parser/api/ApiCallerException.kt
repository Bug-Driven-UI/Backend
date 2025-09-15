package ru.hits.bdui.parser.api

sealed class ApiCallerException(
    override val message: String?,
    override val cause: Throwable?
) : Exception(message, cause) {
    class TimeoutException(message: String) : ApiCallerException(message, null)
    class UnexpectedException(cause: Throwable) : ApiCallerException(null, cause)
    class UnknownMethodException(method: String) :
        ApiCallerException("В запросе указан несуществующий метод $method", null)
}