package ru.hits.bdui.common.models.client.raw.components.properties

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = RenderedSizeRaw.FixedRawRendered::class, name = "fixed"),
    JsonSubTypes.Type(value = RenderedSizeRaw.WeightedRawRendered::class, name = "weighted"),
    JsonSubTypes.Type(value = RenderedSizeRaw.MatchParentRawRendered::class, name = "matchParent"),
    JsonSubTypes.Type(value = RenderedSizeRaw.WrapContentRawRendered::class, name = "wrapContent"),
)
sealed interface RenderedSizeRaw {
    val type: String

    /**
     * @property value >= 0
     */
    data class FixedRawRendered(
        val value: Int
    ) : RenderedSizeRaw {
        override val type: String = "fixed"
    }

    /**
     * @property fraction значение в диапазоне от 0 до 1 (включительно)
     */
    data class WeightedRawRendered(
        val fraction: Double
    ) : RenderedSizeRaw {
        override val type: String = "weighted"
    }

    class MatchParentRawRendered : RenderedSizeRaw {
        override val type: String = "matchParent"
    }

    class WrapContentRawRendered : RenderedSizeRaw {
        override val type: String = "wrapContent"
    }
}
