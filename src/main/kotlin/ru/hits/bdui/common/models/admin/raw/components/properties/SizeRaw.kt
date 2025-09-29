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
    val type: String

    /**
     * @property value >= 0
     */
    data class FixedRaw(
        val value: Int
    ) : SizeRaw {
        override val type: String = "fixed"
    }

    /**
     * @property fraction значение в диапазоне от 0 до 1 (включительно)
     */
    data class WeightedRaw(
        val fraction: Double
    ) : SizeRaw {
        override val type: String = "weighted"
    }

    class MatchParentRaw : SizeRaw {
        override val type: String = "matchParent"
    }

    class WrapContentRaw : SizeRaw {
        override val type: String = "wrapContent"
    }
}