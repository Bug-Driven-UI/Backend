package ru.hits.bdui.common.models.admin.entity.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.TopStartEntity::class, name = "topStart"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.TopCenterEntity::class, name = "topCenter"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.TopEndEntity::class, name = "topEnd"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.CenterStartEntity::class, name = "centerStart"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.CenterEntity::class, name = "center"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.CenterEndEntity::class, name = "centerEnd"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.BottomStartEntity::class, name = "bottomStart"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.BottomCenterEntity::class, name = "bottomCenter"),
    JsonSubTypes.Type(value = HorizontalAndVerticalAlignmentEntity.BottomEndEntity::class, name = "bottomEnd"),
)
sealed interface HorizontalAndVerticalAlignmentEntity {
    data object TopStartEntity : HorizontalAndVerticalAlignmentEntity
    data object TopCenterEntity : HorizontalAndVerticalAlignmentEntity
    data object TopEndEntity : HorizontalAndVerticalAlignmentEntity
    data object CenterStartEntity : HorizontalAndVerticalAlignmentEntity
    data object CenterEntity : HorizontalAndVerticalAlignmentEntity
    data object CenterEndEntity : HorizontalAndVerticalAlignmentEntity
    data object BottomStartEntity : HorizontalAndVerticalAlignmentEntity
    data object BottomCenterEntity : HorizontalAndVerticalAlignmentEntity
    data object BottomEndEntity : HorizontalAndVerticalAlignmentEntity
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