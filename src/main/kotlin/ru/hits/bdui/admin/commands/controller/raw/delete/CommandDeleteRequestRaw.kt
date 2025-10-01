package ru.hits.bdui.admin.commands.controller.raw.delete

import java.util.UUID

data class CommandDeleteRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID
    )
}
