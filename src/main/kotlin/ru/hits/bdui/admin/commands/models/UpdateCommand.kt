package ru.hits.bdui.admin.commands.models

import ru.hits.bdui.domain.command.Command
import java.util.UUID

data class UpdateCommand(
    val id: UUID,
    val command: Command
)