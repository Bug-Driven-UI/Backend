package ru.hits.bdui.common.models.admin.entity.components.additional

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Отвечает за расположение детей в Row
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = HorizontalArrangementEntity.StartEntity::class, name = "start"),
    JsonSubTypes.Type(value = HorizontalArrangementEntity.EndEntity::class, name = "end"),
    JsonSubTypes.Type(value = HorizontalArrangementEntity.CenterEntity::class, name = "center"),
    JsonSubTypes.Type(value = HorizontalArrangementEntity.SpaceBetweenEntity::class, name = "spaceBetween"),
    JsonSubTypes.Type(value = HorizontalArrangementEntity.SpaceEvenlyEntity::class, name = "spaceEvenly"),
    JsonSubTypes.Type(value = HorizontalArrangementEntity.SpaceAroundEntity::class, name = "spaceAround")
)
sealed interface HorizontalArrangementEntity {
    data object StartEntity : HorizontalArrangementEntity
    data object EndEntity : HorizontalArrangementEntity
    data object CenterEntity : HorizontalArrangementEntity
    data object SpaceBetweenEntity : HorizontalArrangementEntity
    data object SpaceEvenlyEntity : HorizontalArrangementEntity
    data object SpaceAroundEntity : HorizontalArrangementEntity
}