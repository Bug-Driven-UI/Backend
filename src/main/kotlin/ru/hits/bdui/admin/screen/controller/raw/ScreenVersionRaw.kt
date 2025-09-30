package ru.hits.bdui.admin.screen.controller.raw

import java.util.UUID

data class ScreenVersionRaw(
    val id: UUID,
    val version: Int,
    val isProduction: Boolean,
    val createdAtTimestampMs: Long,
    val lastModifiedTimestampMs: Long?
)