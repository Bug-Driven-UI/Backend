package ru.hits.bdui.domain.api.schema

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 * Интерфейс для представления объектов схемы маппинга API
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = Schema.Object::class, name = "object"),
    JsonSubTypes.Type(value = Schema.Array::class, name = "array"),
    JsonSubTypes.Type(value = Schema.String::class, name = "string"),
    JsonSubTypes.Type(value = Schema.Number::class, name = "number"),
)
sealed interface Schema {
    val type: kotlin.String

    data class Object(
        val properties: Map<kotlin.String, Schema>
    ) : Schema {
        override val type = "object"
    }

    data class Array(
        val items: Schema
    ) : Schema {
        override val type = "array"
    }

    data object String : Schema {
        override val type = "string"
    }

    data object Number : Schema {
        override val type = "number"
    }
}
