package ru.hits.bdui.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import ru.hits.bdui.common.exceptions.AlreadyExistsException
import ru.hits.bdui.common.exceptions.BadRequestException
import ru.hits.bdui.common.exceptions.NotFoundException
import ru.hits.bdui.common.models.api.ApiResponse
import ru.hits.bdui.common.models.api.ErrorContentRaw

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(ServerWebInputException::class)
    fun handle(ex: ServerWebInputException): Mono<ResponseEntity<ApiResponse.Error>> {
        log.error("Ошибка инпута", ex)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(
                    listOfNotNull(
                        "Не удалось распарсить тело запроса".let(ErrorContentRaw.Companion::emerge)
                    )
                )
            )
            .toMono()
    }

    @ExceptionHandler(AlreadyExistsException::class)
    fun handle(ex: AlreadyExistsException): Mono<ResponseEntity<ApiResponse.Error>> =
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                ApiResponse.error(
                    listOfNotNull(
                        ex.message?.let(ErrorContentRaw.Companion::emerge)
                    )
                )
            )
            .toMono()

    @ExceptionHandler(BadRequestException::class)
    fun handle(ex: BadRequestException): Mono<ResponseEntity<ApiResponse.Error>> {
        log.error("BadRequest", ex)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(
                    listOfNotNull(
                        ex.message?.let(ErrorContentRaw.Companion::emerge)
                    )
                )
            )
            .toMono()
    }

    @ExceptionHandler(NotFoundException::class)
    fun handle(ex: NotFoundException): Mono<ResponseEntity<ApiResponse.Error>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(
                ApiResponse.error(
                    listOfNotNull(
                        ex.message?.let(ErrorContentRaw.Companion::emerge)
                    )
                )
            )
            .toMono()

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): Mono<ResponseEntity<ApiResponse.Error>> {
        log.error("Произошла неожиданная ошибка", ex)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.error(ErrorContentRaw.emerge("Произошла неожиданная ошибка"))
            )
            .toMono()
    }
}