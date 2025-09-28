package ru.hits.bdui.model.api

import ru.hits.bdui.domain.api.Endpoint
import ru.hits.bdui.domain.api.schema.Schema

data class ApiRepresentationCreateModel(
    val name: String,
    val description: String,
    val params: Set<String>,
    val endpoints: List<Endpoint>,
    val schema: Schema,
    val mappingScript: String,
)
