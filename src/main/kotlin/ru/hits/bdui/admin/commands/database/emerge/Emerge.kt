package ru.hits.bdui.admin.commands.database.emerge

import ru.hits.bdui.admin.commands.database.entity.CommandEntity
import ru.hits.bdui.admin.commands.models.UpdateCommand
import ru.hits.bdui.common.models.admin.entity.utils.toDomain
import ru.hits.bdui.common.models.admin.entity.utils.toEntity
import ru.hits.bdui.domain.command.Command
import ru.hits.bdui.domain.command.CommandFromDatabase
import java.time.Instant
import java.util.UUID

fun CommandEntity.Companion.emerge(command: Command): CommandEntity =
    CommandEntity(
        id = UUID.randomUUID(),
        name = command.name.value,
        command = command.toEntity(),
        createdAtTimestampMs = Instant.now().toEpochMilli(),
        lastModifiedAtTimestampMs = null,
    )

fun CommandEntity.Companion.emerge(data: CommandFromDatabase, updateCommand: UpdateCommand): CommandEntity =
    CommandEntity(
        id = data.id,
        name = updateCommand.command.name.value,
        command = updateCommand.command.toEntity(),
        createdAtTimestampMs = Instant.now().toEpochMilli(),
        lastModifiedAtTimestampMs = Instant.now().toEpochMilli(),
    )

fun CommandFromDatabase.Companion.emerge(entity: CommandEntity): CommandFromDatabase =
    CommandFromDatabase(
        id = entity.id,
        command = entity.command.toDomain(),
        createdAtTimestampMs = entity.createdAtTimestampMs,
        lastModifiedTimestampMs = entity.lastModifiedAtTimestampMs,
    )