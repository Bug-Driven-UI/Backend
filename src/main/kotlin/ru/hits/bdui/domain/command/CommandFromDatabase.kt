package ru.hits.bdui.domain.command

import java.util.UUID

/**
 * Обертка команды для ответа из базы данных
 *
 * @property id Идентификатор команды
 * @property createdAtTimestampMs время создания данного экрана
 * @property lastModifiedTimestampMs время создания данного экрана
 * @property command команда
 */
data class CommandFromDatabase(
    val id: UUID,
    val createdAtTimestampMs: Long,
    val lastModifiedTimestampMs: Long?,
    val command: Command
)