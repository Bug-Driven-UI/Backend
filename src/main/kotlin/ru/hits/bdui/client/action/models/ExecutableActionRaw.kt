package ru.hits.bdui.client.action.models

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = CommandRenderedActionRaw::class, name = "command"),
    JsonSubTypes.Type(value = UpdateScreenRenderedActionRaw::class, name = "updateScreen"),
    JsonSubTypes.Type(value = NavigateToRenderedActionRaw::class, name = "navigateTo"),
    JsonSubTypes.Type(value = NavigateBackRenderedActionRaw::class, name = "navigateBack"),
)
sealed interface ExecutableActionRaw {
    val type: String
}
