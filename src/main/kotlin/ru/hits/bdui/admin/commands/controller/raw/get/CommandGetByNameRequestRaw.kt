package ru.hits.bdui.admin.commands.controller.raw.get

data class CommandGetByNameRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val query: String,
    )
}
