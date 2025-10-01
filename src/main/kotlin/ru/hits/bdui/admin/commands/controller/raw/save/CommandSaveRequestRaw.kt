package ru.hits.bdui.admin.commands.controller.raw.save

import ru.hits.bdui.admin.commands.controller.raw.CommandForSaveRaw

data class CommandSaveRequestRaw(
    val data: DataRaw
) {
    data class DataRaw(
        val command: CommandForSaveRaw
    )
}