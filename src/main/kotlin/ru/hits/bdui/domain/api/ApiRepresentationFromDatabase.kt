package ru.hits.bdui.domain.api

import java.util.UUID

/**
 * Модель для внешнего API из базы данных
 *
 * @property id Идентификатор API
 * @property createdAtTimestampMs время создания данного экрана
 * @property lastModifiedTimestampMs время создания данного экрана
 */
data class ApiRepresentationFromDatabase(
    val id: UUID,
    val api: ApiRepresentation,
    val createdAtTimestampMs: Long,
    val lastModifiedTimestampMs: Long?
)