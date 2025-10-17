package ru.hits.bdui.domain.api

import java.util.UUID

/**
 * Короткое представление внешнего API
 */
data class ShortApiRepresentation(
    val id: UUID,
    val name: String,
    val description: String,
)
