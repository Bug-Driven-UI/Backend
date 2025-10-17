package ru.hits.bdui.common.models.admin.raw.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Отвечает за расположение детей в Column
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = VerticalArrangementRaw.TopRaw::class, name = "top"),
    JsonSubTypes.Type(value = VerticalArrangementRaw.BottomRaw::class, name = "bottom"),
    JsonSubTypes.Type(value = VerticalArrangementRaw.CenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = VerticalArrangementRaw.SpaceBetweenRaw::class, name = "spaceBetween"),
    JsonSubTypes.Type(value = VerticalArrangementRaw.SpaceEvenlyRaw::class, name = "spaceEvenly"),
    JsonSubTypes.Type(value = VerticalArrangementRaw.SpaceAroundRaw::class, name = "spaceAround")
)
sealed interface VerticalArrangementRaw {
    data object TopRaw : VerticalArrangementRaw
    data object BottomRaw : VerticalArrangementRaw
    data object CenterRaw : VerticalArrangementRaw
    data object SpaceBetweenRaw : VerticalArrangementRaw
    data object SpaceEvenlyRaw : VerticalArrangementRaw
    data object SpaceAroundRaw : VerticalArrangementRaw
}