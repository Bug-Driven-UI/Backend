package ru.hits.bdui.admin.externalApi.controller.raw

import ru.hits.bdui.domain.api.Endpoint
import ru.hits.bdui.domain.api.schema.Schema

data class ApiRepresentationCreateRequestRaw(
    val name: String,
    val description: String,
    val params: Set<String>,
    val endpoints: List<Endpoint>,
    val schema: Schema,
    val mappingScript: String,
)
