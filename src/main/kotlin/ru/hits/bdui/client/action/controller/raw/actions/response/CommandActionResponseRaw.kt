package ru.hits.bdui.client.action.controller.raw.actions.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeName
import ru.hits.bdui.client.action.ActionResponse
import ru.hits.bdui.common.models.client.raw.components.RenderedComponentRaw

@JsonTypeName("command")
data class CommandActionResponseRaw(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("response")
    val response: CommandResponsePayload
) : ActionResponse

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