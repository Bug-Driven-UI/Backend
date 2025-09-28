package ru.hits.bdui.core.api.model

import com.fasterxml.jackson.databind.JsonNode
import java.util.UUID

typealias ParameterName = String

data class ExternalApiCallModel(
    val apiRepresentationId: UUID,
    val resultAlias: String,
    val parameterValues: Map<ParameterName, JsonNode>,
)
