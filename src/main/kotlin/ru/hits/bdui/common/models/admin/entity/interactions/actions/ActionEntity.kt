package ru.hits.bdui.common.models.admin.entity.interactions.actions

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = CommandActionEntity::class, name = "command"),
    JsonSubTypes.Type(value = UpdateScreenActionEntity::class, name = "updateScreen"),
    JsonSubTypes.Type(value = NavigateToActionEntity::class, name = "navigateTo"),
    JsonSubTypes.Type(value = NavigateBackActionEntity::class, name = "navigateBack"),
)
sealed interface ActionEntity {
    val type: String
}