package ru.hits.bdui.common.models.admin.entity.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentEntity.TopEntity::class, name = "top"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentEntity.BottomEntity::class, name = "bottom"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentEntity.VCenterEntity::class, name = "v-center"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentEntity.HCenterEntity::class, name = "h-center"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentEntity.StartEntity::class, name = "start"),
    JsonSubTypes.Type(value = HorizontalOrVerticalAlignmentEntity.EndEntity::class, name = "end"),
)
sealed interface HorizontalOrVerticalAlignmentEntity {
    data object TopEntity : HorizontalOrVerticalAlignmentEntity
    data object VCenterEntity : HorizontalOrVerticalAlignmentEntity
    data object BottomEntity : HorizontalOrVerticalAlignmentEntity
    data object StartEntity : HorizontalOrVerticalAlignmentEntity
    data object HCenterEntity : HorizontalOrVerticalAlignmentEntity
    data object EndEntity : HorizontalOrVerticalAlignmentEntity
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = VerticalAlignmentEntity.TopEntity::class, name = "top"),
    JsonSubTypes.Type(value = VerticalAlignmentEntity.CenterEntity::class, name = "center"),
    JsonSubTypes.Type(value = VerticalAlignmentEntity.BottomEntity::class, name = "bottom"),
)
sealed interface VerticalAlignmentEntity {
    data object TopEntity : VerticalAlignmentEntity
    data object CenterEntity : VerticalAlignmentEntity
    data object BottomEntity : VerticalAlignmentEntity
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalAlignmentEntity.StartEntity::class, name = "start"),
    JsonSubTypes.Type(value = HorizontalAlignmentEntity.CenterEntity::class, name = "center"),
    JsonSubTypes.Type(value = HorizontalAlignmentEntity.EndEntity::class, name = "end"),
)
sealed interface HorizontalAlignmentEntity {
    data object StartEntity : HorizontalAlignmentEntity
    data object CenterEntity : HorizontalAlignmentEntity
    data object EndEntity : HorizontalAlignmentEntity
}