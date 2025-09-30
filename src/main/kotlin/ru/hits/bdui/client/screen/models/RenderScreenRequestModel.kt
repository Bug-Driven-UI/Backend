package ru.hits.bdui.client.screen.models

import com.fasterxml.jackson.databind.JsonNode

data class RenderScreenRequestModel(
    val screenName: String,
    val variables: Map<String, JsonNode>,
) {
    companion object
}
