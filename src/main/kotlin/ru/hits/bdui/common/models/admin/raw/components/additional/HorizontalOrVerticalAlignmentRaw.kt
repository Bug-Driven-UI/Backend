package ru.hits.bdui.common.models.admin.raw.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentRaw.TopRaw::class, name = "top"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentRaw.BottomRaw::class, name = "bottom"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentRaw.VCenterRaw::class, name = "v-center"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentRaw.HCenterRaw::class, name = "h-center"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentRaw.StartRaw::class, name = "start"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentRaw.EndRaw::class, name = "end"),
)
sealed interface HorizontalOrVerticalAlignmentRaw {
    data object TopRaw : HorizontalOrVerticalAlignmentRaw
    data object VCenterRaw : HorizontalOrVerticalAlignmentRaw
    data object BottomRaw : HorizontalOrVerticalAlignmentRaw
    data object StartRaw : HorizontalOrVerticalAlignmentRaw
    data object HCenterRaw : HorizontalOrVerticalAlignmentRaw
    data object EndRaw : HorizontalOrVerticalAlignmentRaw
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