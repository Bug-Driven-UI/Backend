package ru.hits.bdui.domain.screen

/**
 * Доменное представления ответа на получение экрана
 */
sealed interface ScreenResponse {
    data class Success(val screen: Screen) : ScreenResponse

    data class Error(val errors: List<ErrorContent>) : ScreenResponse
}

/**
 * Содержимое ошибки
 *
 * @property timestampMs время формирования ошибки
 * @property message Описание ошибки
 */
data class ErrorContent(
    val timestampMs: Long,
    val message: String
) {
    companion object {
        fun emerge(message: String): ErrorContent =
            ErrorContent(
                timestampMs = System.currentTimeMillis(),
                message = message
            )
    }
}