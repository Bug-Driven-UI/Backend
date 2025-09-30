package ru.hits.bdui.domain.screen

import java.util.UUID

/**
 * Модель для представления версий экранов
 *
 * @property id идентификатор версии
 * @property version номер версии
 * @property isProduction является ли продовой
 * @property createdAtTimestampMs время создания данного экрана
 * @property lastModifiedTimestampMs время создания данного экрана
 */
data class ScreenVersion(
    val id: UUID,
    val version: Int,
    val isProduction: Boolean,
    val createdAtTimestampMs: Long,
    val lastModifiedTimestampMs: Long?
)