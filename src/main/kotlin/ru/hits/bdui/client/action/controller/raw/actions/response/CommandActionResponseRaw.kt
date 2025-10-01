package ru.hits.bdui.client.action.controller.raw.actions.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.client.raw.components.RenderedComponentRaw

data class CommandActionResponseRaw(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("response")
    val response: CommandResponsePayload
) : ActionResponseRaw {
    override val type: String = "command"
}

data class CommandResponsePayload(
    @JsonProperty("data")
    val data: CommandResponseData
)

data class CommandResponseData(
    @JsonProperty("component")
    val component: RenderedComponentRaw? = null,

    @JsonProperty("fallbackMessage")
    val fallbackMessage: String? = null
)