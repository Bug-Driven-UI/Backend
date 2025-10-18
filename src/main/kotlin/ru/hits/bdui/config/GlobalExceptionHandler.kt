package ru.hits.bdui.config

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.slf4j.LoggerFactory
import org.springframework.core.codec.DecodingException
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

        val message = friendlyMessage(ex)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse.error(
                    listOfNotNull(
                        ErrorContentRaw.emerge(message)
                    )
                )
            )
            .toMono()
    }

    private fun friendlyMessage(ex: ServerWebInputException): String {
        val allCauses = generateSequence<Throwable>(ex) { it.cause }.toList()

        val jme = allCauses.firstOrNull { it is JsonMappingException } as JsonMappingException?
        if (jme != null) {
            val path = jsonPath(jme)

            return when (jme) {
                is MissingKotlinParameterException ->
                    "Поле \"$path\" обязательно для заполнения"

                is InvalidFormatException -> {
                    val expected = jme.targetType?.simpleName ?: "нужный тип"
                    val got = jme.value?.toString()?.take(60)
                    if (got != null) "Поле \"$path\" имеет неверный формат: ожидается $expected, получено \"$got\""
                    else "Поле \"$path\" имеет неверный формат: ожидается $expected"
                }

                is MismatchedInputException -> {
                    val expected = jme.targetType?.simpleName ?: "нужный тип"
                    "Поле \"$path\" имеет неверный тип: ожидается $expected"
                }

                else -> "Поле \"$path\" некорректно заполнено"
            }
        }

        val parse = allCauses.firstOrNull { it is JsonParseException } as JsonParseException?
        if (parse != null) {
            return "Некорректный JSON: ${parse.originalMessage}"
        }

        val dec = allCauses.firstOrNull { it is DecodingException } as DecodingException?
        if (dec != null) {
            return "Не удалось распарсить тело запроса"
        }

        return ex.reason ?: "Не удалось распарсить тело запроса"
    }

    private fun jsonPath(e: JsonMappingException): String {
        val raw = e.path.joinToString(".") { ref ->
            when {
                ref.fieldName != null -> ref.fieldName
                ref.index >= 0 -> "[${ref.index}]"
                else -> "?"
            }
        }
        return raw.replace(".[", "[")
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