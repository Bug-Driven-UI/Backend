package ru.hits.bdui.domain.api

import com.fasterxml.jackson.databind.JsonNode
import java.util.UUID

/**
 * Информация о вызове внешнего API
 *
 * @property apiResultAlias алиас для использования результатов вызова API
 * @property apiId id API из реестра внешних API
 * @property apiParams параметры для передачи в API формата <названиеПараметра>=<значение>
 */
data class ApiCallRepresentation(
    val apiResultAlias: String,
    val apiId: UUID,
    val apiParams: Map<String, JsonNode>,
)
