package ru.hits.bdui.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

//@RestControllerAdvice
//class GlobalExceptionHandler {
//    private val log = LoggerFactory.getLogger(this::class.java)
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception::class)
//    fun handleException(exception: Exception) {
//        log.error("Неожиданная ошибка", exception)
//    }
//}