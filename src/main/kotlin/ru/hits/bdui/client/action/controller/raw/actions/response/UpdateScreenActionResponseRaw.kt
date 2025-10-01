package ru.hits.bdui.client.action.controller.raw.actions.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.client.raw.components.RenderedComponentRaw

data class UpdateScreenActionResponseRaw(
    @JsonProperty("response")
    val response: UpdateScreenResponsePayloadRaw
) : ActionResponseRaw {
    override val type: String = "updateScreen"
}

data class UpdateScreenResponsePayloadRaw(
    @JsonProperty("data")
    val data: List<UpdateInstructionRaw>
)

data class UpdateInstructionRaw(
    @JsonProperty("target")
    val target: String,

    @JsonProperty("method")
    val method: UpdateMethodRaw,

    @JsonProperty("content")
    val content: RenderedComponentRaw? = null
)

enum class UpdateMethodRaw {
    @JsonProperty("update")
    UPDATE,

    @JsonProperty("insert")
    INSERT,

    @JsonProperty("delete")
    DELETE
}