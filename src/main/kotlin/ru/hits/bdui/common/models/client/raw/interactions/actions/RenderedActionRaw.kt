package ru.hits.bdui.common.models.client.raw.interactions.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = CommandRenderedActionRaw::class, name = "command"),
    JsonSubTypes.Type(value = UpdateScreenRenderedActionRaw::class, name = "updateScreen"),
)
sealed interface RenderedActionRaw {
    val type: String
}
