package ru.hits.bdui.admin.templates.controller.raw.get

data class ComponentTemplateGetByTokenRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val query: String,
    )
}
