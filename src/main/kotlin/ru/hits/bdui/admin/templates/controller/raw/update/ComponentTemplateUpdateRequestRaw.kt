package ru.hits.bdui.admin.templates.controller.raw.update

import ru.hits.bdui.admin.templates.controller.raw.ComponentTemplateForUpdateRaw
import java.util.UUID

data class ComponentTemplateUpdateRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID,
        val template: ComponentTemplateForUpdateRaw
    )
}
