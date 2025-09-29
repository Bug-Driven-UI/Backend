package ru.hits.bdui.admin.templates.controller.raw.delete

import java.util.UUID

data class ComponentTemplateDeleteRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}
