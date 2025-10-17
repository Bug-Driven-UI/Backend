package ru.hits.bdui.common.models.client.raw.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Отвечает за расположение детей в Row
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = RenderedHorizontalArrangementRaw.RenderedStartRaw::class, name = "start"),
    JsonSubTypes.Type(value = RenderedHorizontalArrangementRaw.RenderedEndRaw::class, name = "end"),
    JsonSubTypes.Type(value = RenderedHorizontalArrangementRaw.RenderedCenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = RenderedHorizontalArrangementRaw.RenderedSpaceBetweenRaw::class, name = "spaceBetween"),
    JsonSubTypes.Type(value = RenderedHorizontalArrangementRaw.RenderedSpaceEvenlyRaw::class, name = "spaceEvenly"),
    JsonSubTypes.Type(value = RenderedHorizontalArrangementRaw.RenderedSpaceAroundRaw::class, name = "spaceAround")
)
sealed interface RenderedHorizontalArrangementRaw {
    data object RenderedStartRaw : RenderedHorizontalArrangementRaw
    data object RenderedEndRaw : RenderedHorizontalArrangementRaw
    data object RenderedCenterRaw : RenderedHorizontalArrangementRaw
    data object RenderedSpaceBetweenRaw : RenderedHorizontalArrangementRaw
    data object RenderedSpaceEvenlyRaw : RenderedHorizontalArrangementRaw
    data object RenderedSpaceAroundRaw : RenderedHorizontalArrangementRaw
}