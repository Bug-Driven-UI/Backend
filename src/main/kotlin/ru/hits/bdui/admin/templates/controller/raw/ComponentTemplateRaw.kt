package ru.hits.bdui.admin.templates.controller.raw

import ru.hits.bdui.common.models.admin.raw.components.ComponentRaw
import java.util.UUID

data class ComponentTemplateRaw(
    val id: UUID,
    val name: String,
    val component: ComponentRaw,
    val createdAtTimestampMs: Long,
    val lastModifiedAtTimestampMs: Long?
) {
    companion object
}

data class ComponentTemplateForSaveRaw(
    val name: String,
    val component: ComponentRaw
)