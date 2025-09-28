package ru.hits.bdui.domain.template

import java.util.UUID

/**
 * Обертка шаблон компонента для ответа из базы данных
 *
 * @property id Идентификатор команды
 * @property createdAtTimestampMs время создания данного экрана
 * @property lastModifiedTimestampMs время создания данного экрана
 * @property template шаблон компонента
 */
data class ComponentTemplateFromDatabase(
    val id: UUID,
    val createdAtTimestampMs: Long,
    val lastModifiedTimestampMs: Long?,
    val template: ComponentTemplate,
) {
    companion object
}