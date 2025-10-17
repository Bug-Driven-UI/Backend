package ru.hits.bdui.common.models.admin.entity.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Отвечает за расположение детей в Column
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = VerticalArrangementEntity.TopEntity::class, name = "top"),
    JsonSubTypes.Type(value = VerticalArrangementEntity.BottomEntity::class, name = "bottom"),
    JsonSubTypes.Type(value = VerticalArrangementEntity.CenterEntity::class, name = "center"),
    JsonSubTypes.Type(value = VerticalArrangementEntity.SpaceBetweenEntity::class, name = "spaceBetween"),
    JsonSubTypes.Type(value = VerticalArrangementEntity.SpaceEvenlyEntity::class, name = "spaceEvenly"),
    JsonSubTypes.Type(value = VerticalArrangementEntity.SpaceAroundEntity::class, name = "spaceAround")
)
sealed interface VerticalArrangementEntity {
    data object TopEntity : VerticalArrangementEntity
    data object BottomEntity : VerticalArrangementEntity
    data object CenterEntity : VerticalArrangementEntity
    data object SpaceBetweenEntity : VerticalArrangementEntity
    data object SpaceEvenlyEntity : VerticalArrangementEntity
    data object SpaceAroundEntity : VerticalArrangementEntity
}