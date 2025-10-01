package ru.hits.bdui.client.action.controller.raw.actions.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(CommandActionResponseRaw::class, name = "command"),
    JsonSubTypes.Type(UpdateScreenActionResponse::class, name = "updateScreen")
)
abstract class ActionResponseRaw(
    @JsonProperty("type")
    val type: String
)