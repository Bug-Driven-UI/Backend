package ru.hits.bdui.domain.api

import ru.hits.bdui.domain.ApiName
import ru.hits.bdui.domain.api.schema.Schema

/**
 * Модель для внешнего API
 *
 * @param name название API для использования
 * @param description краткое описание
 * @param params требуемые параметры
 * @param endpoints эндпоинты для запросов данных
 * @param schema схема для маппинга данных
 * @param mappingScript JS скрипт для преобразования полученных данных в схему
 */
data class ApiRepresentation(
    val name: ApiName,
    val description: String,
    val params: Set<String>,
    val endpoints: List<ShortApiRepresentation>,
    val schema: Schema,
    val mappingScript: String
)