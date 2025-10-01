package ru.hits.bdui.client.action.controller.raw.actions.request

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.hits.bdui.common.models.admin.raw.interactions.actions.CommandActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.NavigateBackActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.NavigateToActionRaw
import ru.hits.bdui.common.models.admin.raw.interactions.actions.UpdateScreenActionRaw


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = CommandActionRaw::class, name = "command"),
    JsonSubTypes.Type(value = UpdateScreenActionRaw::class, name = "updateScreen"),
    JsonSubTypes.Type(value = NavigateToActionRaw::class, name = "navigateTo"),
    JsonSubTypes.Type(value = NavigateBackActionRaw::class, name = "navigateBack"),
)
sealed interface ActionRawRequest {
    val type: String
}