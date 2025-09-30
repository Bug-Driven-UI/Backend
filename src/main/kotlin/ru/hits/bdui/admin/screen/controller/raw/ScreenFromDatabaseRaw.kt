package ru.hits.bdui.admin.screen.controller.raw

import ru.hits.bdui.common.models.admin.raw.components.ComponentRaw
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
data class ScreenFromDatabaseRaw(
    val screenId: UUID,
    val screenName: String,
    val version: ScreenVersionRaw,
    val description: String,
    val screenNavigationParams: Set<String>,
    val apis: List<ApiCallRepresentationRaw>,
    val components: List<ComponentRaw>,
    val scaffold: ScaffoldRaw?,
) {
    companion object
}