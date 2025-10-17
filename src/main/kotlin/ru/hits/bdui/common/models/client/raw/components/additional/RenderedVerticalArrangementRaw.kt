package ru.hits.bdui.common.models.client.raw.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Отвечает за расположение детей в Column
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = RenderedVerticalArrangementRaw.RenderedTopRaw::class, name = "top"),
    JsonSubTypes.Type(value = RenderedVerticalArrangementRaw.RenderedBottomRaw::class, name = "bottom"),
    JsonSubTypes.Type(value = RenderedVerticalArrangementRaw.RenderedCenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = RenderedVerticalArrangementRaw.RenderedSpaceBetweenRaw::class, name = "spaceBetween"),
    JsonSubTypes.Type(value = RenderedVerticalArrangementRaw.RenderedSpaceEvenlyRaw::class, name = "spaceEvenly"),
    JsonSubTypes.Type(value = RenderedVerticalArrangementRaw.RenderedSpaceAroundRaw::class, name = "spaceAround")
)
sealed interface RenderedVerticalArrangementRaw {
    data object RenderedTopRaw : RenderedVerticalArrangementRaw
    data object RenderedBottomRaw : RenderedVerticalArrangementRaw
    data object RenderedCenterRaw : RenderedVerticalArrangementRaw
    data object RenderedSpaceBetweenRaw : RenderedVerticalArrangementRaw
    data object RenderedSpaceEvenlyRaw : RenderedVerticalArrangementRaw
    data object RenderedSpaceAroundRaw : RenderedVerticalArrangementRaw
}