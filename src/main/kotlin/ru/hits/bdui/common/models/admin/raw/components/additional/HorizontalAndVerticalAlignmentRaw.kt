package ru.hits.bdui.common.models.admin.raw.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.TopStartRaw::class, name = "topStart"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.TopCenterRaw::class, name = "topCenter"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.TopEndRaw::class, name = "topEnd"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.CenterStartRaw::class, name = "centerStart"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.CenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.CenterEndRaw::class, name = "centerEnd"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.BottomStartRaw::class, name = "bottomStart"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.BottomCenterRaw::class, name = "bottomCenter"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentRaw.BottomEndRaw::class, name = "bottomEnd"),
)
sealed interface HorizontalAndVerticalAlignmentRaw {
    data object TopStartRaw : HorizontalAndVerticalAlignmentRaw
    data object TopCenterRaw : HorizontalAndVerticalAlignmentRaw
    data object TopEndRaw : HorizontalAndVerticalAlignmentRaw
    data object CenterStartRaw : HorizontalAndVerticalAlignmentRaw
    data object CenterRaw : HorizontalAndVerticalAlignmentRaw
    data object CenterEndRaw : HorizontalAndVerticalAlignmentRaw
    data object BottomStartRaw : HorizontalAndVerticalAlignmentRaw
    data object BottomCenterRaw : HorizontalAndVerticalAlignmentRaw
    data object BottomEndRaw : HorizontalAndVerticalAlignmentRaw
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = VerticalAlignmentRaw.TopRaw::class, name = "top"),
    JsonSubTypes.Type(value = VerticalAlignmentRaw.CenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = VerticalAlignmentRaw.BottomRaw::class, name = "bottom"),
)
sealed interface VerticalAlignmentRaw {
    data object TopRaw : VerticalAlignmentRaw
    data object CenterRaw : VerticalAlignmentRaw
    data object BottomRaw : VerticalAlignmentRaw
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalAlignmentRaw.StartRaw::class, name = "start"),
    JsonSubTypes.Type(value = HorizontalAlignmentRaw.CenterRaw::class, name = "center"),
    JsonSubTypes.Type(value = HorizontalAlignmentRaw.EndRaw::class, name = "end"),
)
sealed interface HorizontalAlignmentRaw {
    data object StartRaw : HorizontalAlignmentRaw
    data object CenterRaw : HorizontalAlignmentRaw
    data object EndRaw : HorizontalAlignmentRaw
}