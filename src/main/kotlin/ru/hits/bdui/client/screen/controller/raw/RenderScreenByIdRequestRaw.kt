package ru.hits.bdui.client.screen.controller.raw

import com.fasterxml.jackson.databind.JsonNode
import java.util.UUID

data class RenderScreenByIdRequestRaw(
    val screenId: UUID,
    val versionId: UUID,
    val variables: Map<String, JsonNode>?,
)
