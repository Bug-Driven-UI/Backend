package ru.hits.bdui.admin.commands.controller.raw

data class CommandResponseRaw(
    val command: CommandFromDatabaseRaw
)

data class CommandListResponseRaw(
    val commands: List<CommandFromDatabaseRaw>
)