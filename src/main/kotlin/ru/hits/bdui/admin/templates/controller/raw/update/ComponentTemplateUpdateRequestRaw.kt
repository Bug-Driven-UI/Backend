package ru.hits.bdui.admin.templates.controller.raw.update

import ru.hits.bdui.admin.templates.controller.raw.ComponentTemplateForSaveRaw
import java.util.UUID

data class ComponentTemplateUpdateRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID,
        val template: ComponentTemplateForSaveRaw
    )
}
