package ru.hits.bdui.client.screen.controller.raw

import com.fasterxml.jackson.databind.JsonNode

data class RenderScreenRequestRaw(
    val screenName: String,
    val variables: Map<String, JsonNode>,
)
