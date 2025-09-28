package ru.hits.bdui.domain.api

import ru.hits.bdui.domain.ApiName

/**
 * Краткая информация об API
 *
 * @property apiAlias алиас для использования результатов вызова API
 * @property apiName название API из реестра внешних API
 * @property apiParams требуемые параметры для выполнения запроса
 */
data class ShortApiRepresentation(
    val apiAlias: String,
    val apiName: ApiName,
    val apiParams: List<String>
)