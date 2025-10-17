package ru.hits.bdui.common.models.admin.raw.components.properties

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = SizeRaw.FixedRaw::class, name = "fixed"),
    JsonSubTypes.Type(value = SizeRaw.WeightedRaw::class, name = "weighted"),
    JsonSubTypes.Type(value = SizeRaw.MatchParentRaw::class, name = "matchParent"),
    JsonSubTypes.Type(value = SizeRaw.WrapContentRaw::class, name = "wrapContent"),
)
sealed interface SizeRaw {
    /**
     * @property value >= 0
     */
    data class FixedRaw(
        val value: Int
    ) : SizeRaw

    /**
     * @property fraction значение в диапазоне от 0 до 1 (включительно)
     */
    data class WeightedRaw(
        val fraction: Double
    ) : SizeRaw

    data object MatchParentRaw : SizeRaw

    data object WrapContentRaw : SizeRaw
}