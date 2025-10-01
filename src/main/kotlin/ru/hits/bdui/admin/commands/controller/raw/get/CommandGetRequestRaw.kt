package ru.hits.bdui.admin.commands.controller.raw.get

import java.util.UUID

data class CommandGetRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}
