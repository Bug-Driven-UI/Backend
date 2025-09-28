package ru.hits.bdui.admin.templates.controller.raw

import ru.hits.bdui.domain.screen.components.Component
import java.util.UUID

data class ComponentTemplateRaw(
    val id: UUID,
    val name: String,
    val component: Component, //TODO(Воссоздать структуру компонентов для сырого представления)
    val createdAtTimestampMs: Long,
    val lastModifiedAtTimestampMs: Long?
) {
    companion object
}

data class ComponentTemplateForSaveRaw(
    val name: String,
    val component: Component
)