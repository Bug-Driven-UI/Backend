package ru.hits.bdui.admin.templates.controller.raw.save

import ru.hits.bdui.admin.templates.controller.raw.ComponentTemplateForSaveRaw

data class ComponentTemplateSaveRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val template: ComponentTemplateForSaveRaw
    )
}
