package ru.hits.bdui.client.screen.controller.raw

import com.fasterxml.jackson.databind.JsonNode
import ru.hits.bdui.common.models.client.raw.components.RenderedComponentRaw

data class RenderedScreenRawWrapper(
    val screen: RenderedScreenRaw,
) {
    companion object
}

data class RenderedScreenRaw(
    val screenName: String,
    val version: Int,
    val localStates: Map<String, JsonNode>?,
    val components: List<RenderedComponentRaw>,
    val scaffold: RenderedScaffoldRaw?,
)
