package ru.hits.bdui.admin.commands.controller.raw.update

import ru.hits.bdui.admin.commands.controller.raw.CommandForSaveRaw
import java.util.UUID

data class CommandUpdateRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val id: UUID,
        val command: CommandForSaveRaw
    )
}