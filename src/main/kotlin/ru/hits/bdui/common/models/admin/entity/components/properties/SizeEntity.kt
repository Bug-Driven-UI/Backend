package ru.hits.bdui.common.models.admin.entity.components.properties

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SizeEntity.FixedEntity::class, name = "fixed"),
    JsonSubTypes.Type(value = SizeEntity.WeightedEntity::class, name = "weighted"),
    JsonSubTypes.Type(value = SizeEntity.MatchParentEntity::class, name = "matchParent"),
    JsonSubTypes.Type(value = SizeEntity.WrapContentEntity::class, name = "wrapContent"),
)
sealed interface SizeEntity {
    /**
     * @property value >= 0
     */
    data class FixedEntity(
        val value: Int
    ) : SizeEntity

    /**
     * @property fraction значение в диапазоне от 0 до 1 (включительно)
     */
    data class WeightedEntity(
        val fraction: Double
    ) : SizeEntity

    data object MatchParentEntity : SizeEntity

    data object WrapContentEntity : SizeEntity
}