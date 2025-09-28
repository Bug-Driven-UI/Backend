package ru.hits.bdui.admin.templates.controller.raw.get

import java.util.UUID

data class ComponentTemplateGetRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}
