package ru.hits.bdui.common.models.admin.raw.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Отвечает за расположение детей в Row
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalArrangementRaw.StartRaw::class, name = "start"),
    JsonSubTypes.Type(value = HorizontalArrangementRaw.EndRaw::class, name = "end"),
    JsonSubTypes.Type(value = HorizontalArrangementRaw.CenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = HorizontalArrangementRaw.SpaceBetweenRaw::class, name = "spaceBetween"),
    JsonSubTypes.Type(value = HorizontalArrangementRaw.SpaceEvenlyRaw::class, name = "spaceEvenly"),
    JsonSubTypes.Type(value = HorizontalArrangementRaw.SpaceAroundRaw::class, name = "spaceAround")
)
sealed interface HorizontalArrangementRaw {
    data object StartRaw : HorizontalArrangementRaw
    data object EndRaw : HorizontalArrangementRaw
    data object CenterRaw : HorizontalArrangementRaw
    data object SpaceBetweenRaw : HorizontalArrangementRaw
    data object SpaceEvenlyRaw : HorizontalArrangementRaw
    data object SpaceAroundRaw : HorizontalArrangementRaw
}