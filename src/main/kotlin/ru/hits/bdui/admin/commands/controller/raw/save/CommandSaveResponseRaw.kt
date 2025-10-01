package ru.hits.bdui.admin.commands.controller.raw.save

import ru.hits.bdui.admin.commands.controller.raw.CommandFromDatabaseRaw

data class CommandSaveResponseRaw(
    val command: CommandFromDatabaseRaw
)