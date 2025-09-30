package ru.hits.bdui.common.models.admin.entity.screen

import com.fasterxml.jackson.databind.JsonNode
import ru.hits.bdui.common.models.admin.entity.components.ComponentEntity
import java.util.UUID

/**
 * Представление экрана
 *
 * @property screenName название экрана
 * @property description краткое описание экрана
 * @property screenNavigationParams параметры, требующиеся для загрузки данного экрана
 * @property apis внешние API, требуемые для загрузки данного экрана, где ключ - alias для обращения к данным из API
 * @property components список компонентов экрана
 * @property scaffold скаффолд экрана
 */
data class ScreenEntity(
    val screenName: String,
    val description: String,
    val screenNavigationParams: Set<String>,
    val apis: List<ApiCallRepresentationEntity>,
    val components: List<ComponentEntity>,
    val scaffold: ScaffoldEntity?,
)

/**
 * Информация о вызове внешнего API
 *
 * @property apiResultAlias алиас для использования результатов вызова API
 * @property apiId id API из реестра внешних API
 * @property apiParams параметры для передачи в API формата <названиеПараметра>=<значение>
 */
data class ApiCallRepresentationEntity(
    val apiResultAlias: String,
    val apiId: UUID,
    val apiParams: Map<String, JsonNode>,
)
