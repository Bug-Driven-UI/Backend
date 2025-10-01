package ru.hits.bdui.common.models.admin.entity.components

import java.util.UUID

/**
 * Шаблон компонента
 */
data class ComponentTemplateFromDatabaseEntity(
    val id: UUID,
    val createdAtTimestampMs: Long,
    val lastModifiedTimestampMs: Long?,
    val template: ComponentTemplateEntity
)