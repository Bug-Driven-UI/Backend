package ru.hits.bdui.client.action.controller.raw.actions.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = CommandActionRawRequest::class, name = "command"),
    JsonSubTypes.Type(value = UpdateScreenActionRawRequest::class, name = "updateScreen"),
    JsonSubTypes.Type(value = NavigateToActionRawRequest::class, name = "navigateTo"),
    JsonSubTypes.Type(value = NavigateBackActionRawRequest::class, name = "navigateBack"),
)
sealed interface ActionRawRequest {
    val type: String
}