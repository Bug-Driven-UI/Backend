package ru.hits.bdui.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message: String) : Exception(message) {
}

inline fun <reified T> notFound(id: UUID): NotFoundException =
    NotFoundException("Сущность ${T::class.java.simpleName} с id $id не найдена")

inline fun <reified T> notFound(value: String): NotFoundException =
    NotFoundException("Сущность ${T::class.java.simpleName} со значением $value не найдена")