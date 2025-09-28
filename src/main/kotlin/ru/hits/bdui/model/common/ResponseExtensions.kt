package ru.hits.bdui.model.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

private typealias JsonObject = Map<String, Any?>

private const val DATA = "data"
private const val TYPE = "type"
private const val SUCCESS_TYPE = "success"
private const val ERROR_TYPE = "error"
private const val ERRORS = "errors"

data class ResponseBuilder<S, E>(
    val successResponseCreator: () -> ResponseEntity<S>,
    val errorResponseCreator: (Throwable) -> ResponseEntity<E>,
)

fun <S, E> successResponseBuilderBase(
    block: () -> ResponseEntity<S>,
): ResponseBuilder<S, E> {
    return ResponseBuilder(
        successResponseCreator = block,
        errorResponseCreator = { throw it },
    )
}

fun <S, E> ResponseBuilder<S, E>.errorResponseBuilderBase(
    block: (Throwable) -> ResponseEntity<E>,
): ResponseBuilder<S, E> {
    return this.copy(
        errorResponseCreator = block,
    )
}

fun ResponseBuilder<*, *>.getResponse(): ResponseEntity<*> {
    return runCatching {
        successResponseCreator()
    }.getOrElse { throwable ->
        errorResponseCreator(throwable)
    }
}

fun <T> successResponseBuilder(
    successDataName: String = DATA,
    successStatus: HttpStatus = HttpStatus.OK,
    block: () -> T,
) = successResponseBuilderBase<JsonObject, JsonObject> {
    val successContent = mapOf(
        TYPE to SUCCESS_TYPE,
        successDataName to block(),
    )
    ResponseEntity.status(successStatus).body(successContent)
}

fun ResponseBuilder<JsonObject, JsonObject>.errorResponseBuilder(
    errorStatus: HttpStatus = HttpStatus.OK,
    block: (Throwable) -> List<ErrorModel>,
) = errorResponseBuilderBase { throwable ->
    val errorContent = mapOf(
        TYPE to ERROR_TYPE,
        ERRORS to block(throwable),
    )
    ResponseEntity.status(errorStatus).body(errorContent)
}

fun ResponseBuilder<JsonObject, JsonObject>.defaultErrorResponseBuilder(): ResponseBuilder<JsonObject, JsonObject> =
    errorResponseBuilder { throwable ->
        listOf(
            ErrorModel(
                message = throwable.message.toString(),
                timestampMs = System.currentTimeMillis(),
            )
        )
    }
