package ru.hits.bdui.common.models.admin.raw.interactions.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = CommandActionRaw::class, name = "command"),
    JsonSubTypes.Type(value = UpdateScreenActionRaw::class, name = "updateScreen"),
)
sealed interface ActionRaw {
    val type: String
}