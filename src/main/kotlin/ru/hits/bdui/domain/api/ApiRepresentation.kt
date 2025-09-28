package ru.hits.bdui.domain.api

import ru.hits.bdui.domain.ApiName
import ru.hits.bdui.domain.api.schema.Schema

/**
 * Модель для внешнего API
 *
 * @property name название API для использования
 * @property description краткое описание
 * @property params требуемые параметры
 * @property endpoints эндпоинты для запросов данных
 * @property schema схема для маппинга данных
 * @property mappingScript JS скрипт для преобразования полученных данных в схему
 */
data class ApiRepresentation(
    val name: ApiName,
    val description: String,
    val params: Set<String>,
    val endpoints: List<Endpoint>,
    val schema: Schema?,
    val mappingScript: String?,
)