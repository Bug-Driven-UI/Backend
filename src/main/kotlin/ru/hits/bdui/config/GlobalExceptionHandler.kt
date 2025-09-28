package ru.hits.bdui.config

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.hits.bdui.model.common.defaultErrorResponseBuilder
import ru.hits.bdui.model.common.getResponse
import ru.hits.bdui.model.common.successResponseBuilder

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception) = successResponseBuilder {
        log.error("Неожиданная ошибка", exception)
        throw exception
    }.defaultErrorResponseBuilder().getResponse()
}