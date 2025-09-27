package ru.hits.bdui.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.common.models.raw.ErrorContentRaw
import ru.hits.bdui.common.models.raw.ErrorResponseRaw

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BadRequestException::class)
    fun handle(ex: BadRequestException): Mono<ResponseEntity<ErrorResponseRaw>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponseRaw(
                    errors = listOfNotNull(
                        ex.message?.let(ErrorContentRaw::emerge)
                    )
                )
            )
            .toMono()

    @ExceptionHandler(NotFoundException::class)
    fun handle(ex: NotFoundException): Mono<ResponseEntity<ErrorResponseRaw>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ErrorResponseRaw(
                    errors = listOfNotNull(
                        ex.message?.let(ErrorContentRaw::emerge)
                    )
                )
            )
            .toMono()

    @ExceptionHandler(UnexpectedException::class)
    fun handle(ex: UnexpectedException): Mono<ResponseEntity<ErrorResponseRaw>> {
        log.error("Произошла неожиданная ошибка", ex)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponseRaw(
                    errors = listOf(
                        ErrorContentRaw.emerge("Произошла неожиданная ошибка")
                    )
                )
            )
            .toMono()
    }

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): Mono<ResponseEntity<ErrorResponseRaw>> {
        log.error("Произошла неожиданная ошибка", ex)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponseRaw(
                    errors = listOf(
                        ErrorContentRaw.emerge("Произошла неожиданная ошибка")
                    )
                )
            )
            .toMono()
    }
}