package ru.hits.bdui.admin.commands.controller.raw

import ru.hits.bdui.admin.screen.controller.raw.ApiCallRepresentationRaw
import java.util.UUID

data class CommandFromDatabaseRaw(
    val id: UUID,
    val name: String,
    val apis: List<ApiCallRepresentationRaw>,
    val params: Set<String>,
    val itemTemplateId: UUID?,
    val fallbackMessage: String?,
    val createdAtTimestampMs: Long,
    val lastModifiedAtTimestampMs: Long?
) {
    companion object
}

data class CommandForSaveRaw(
    val name: String,
    val apis: List<ApiCallRepresentationRaw>,
    val params: Set<String>,
    val itemTemplateId: UUID?,
    val fallbackMessage: String?
)