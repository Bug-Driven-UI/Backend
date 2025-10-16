package ru.hits.bdui.client.screen.models

import com.fasterxml.jackson.databind.JsonNode
import java.util.UUID

data class RenderScreenByIdRequestModel(
    val screenId: UUID,
    val versionId: UUID,
    val variables: Map<String, JsonNode>,
) {
    companion object
}
