package ru.hits.bdui.admin.screen.controller.raw.get

data class GetByNameRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(val query: String)
}