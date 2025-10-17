package ru.hits.bdui.common.models.client.raw.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedTopStartRaw::class, name = "topStart"),
    JsonSubTypes.Type(
        value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedTopCenterRaw::class,
        name = "topCenter"
    ),
    JsonSubTypes.Type(value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedTopEndRaw::class, name = "topEnd"),
    JsonSubTypes.Type(
        value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedCenterStartRaw::class,
        name = "centerStart"
    ),
    JsonSubTypes.Type(value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedCenterRaw::class, name = "center"),
    JsonSubTypes.Type(
        value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedCenterEndRaw::class,
        name = "centerEnd"
    ),
    JsonSubTypes.Type(
        value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedBottomStartRaw::class,
        name = "bottomStart"
    ),
    JsonSubTypes.Type(
        value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedBottomCenterRaw::class,
        name = "bottomCenter"
    ),
    JsonSubTypes.Type(
        value = RenderedHorizontalAndVerticalAlignmentRaw.RenderedBottomEndRaw::class,
        name = "bottomEnd"
    ),
)
sealed interface RenderedHorizontalAndVerticalAlignmentRaw {
    data object RenderedTopStartRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedTopCenterRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedTopEndRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedCenterStartRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedCenterRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedCenterEndRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedBottomStartRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedBottomCenterRaw : RenderedHorizontalAndVerticalAlignmentRaw
    data object RenderedBottomEndRaw : RenderedHorizontalAndVerticalAlignmentRaw
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = RenderedVerticalAlignmentRaw.RenderedTopRaw::class, name = "top"),
    JsonSubTypes.Type(value = RenderedVerticalAlignmentRaw.RenderedCenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = RenderedVerticalAlignmentRaw.RenderedBottomRaw::class, name = "bottom"),
)
sealed interface RenderedVerticalAlignmentRaw {
    data object RenderedTopRaw : RenderedVerticalAlignmentRaw
    data object RenderedCenterRaw : RenderedVerticalAlignmentRaw
    data object RenderedBottomRaw : RenderedVerticalAlignmentRaw
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = RenderedHorizontalAlignmentRaw.RenderedStartRaw::class, name = "start"),
    JsonSubTypes.Type(value = RenderedHorizontalAlignmentRaw.RenderedCenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = RenderedHorizontalAlignmentRaw.RenderedEndRaw::class, name = "end"),
)
sealed interface RenderedHorizontalAlignmentRaw {
    data object RenderedStartRaw : RenderedHorizontalAlignmentRaw
    data object RenderedCenterRaw : RenderedHorizontalAlignmentRaw
    data object RenderedEndRaw : RenderedHorizontalAlignmentRaw
}