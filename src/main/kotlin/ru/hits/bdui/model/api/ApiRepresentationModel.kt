package ru.hits.bdui.model.api

import ru.hits.bdui.domain.api.Endpoint
import ru.hits.bdui.domain.api.schema.Schema
import java.util.UUID

data class ApiRepresentationModel(
    val id: UUID,
    val name: String,
    val description: String,
    val params: Set<String>,
    val endpoints: List<Endpoint>,
    val schema: Schema,
    val mappingScript: String,
    val createdAtTimestampMs: Long,
    val lastModifiedAtTimestampMs: Long,
)
