package ru.hits.bdui.domain.api

/**
 * Модель для представления запросов к внешним системам
 *
 * @property url Путь, на который требуется выполнить запрос
 * @property method Метод, с которым требуется выполнить запрос
 * @property responseName Имя для ассоциации ответа с запросом
 * @property timeoutMs Время, за которое должен быть получен ответ на запрос
 * @property isRequired Флаг обязательности ответа, если true, то в случае неудачи при получении ответа, отдаст клиенту ошибку
 */
data class Endpoint(
    val url: String,
    val method: HttpMethod,
    val responseName: String,
    val timeoutMs: Long = 5000,
    val isRequired: Boolean,
)

enum class HttpMethod {
    GET, POST, PUT, DELETE, PATCH
}