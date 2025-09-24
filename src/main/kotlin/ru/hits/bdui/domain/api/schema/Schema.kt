package ru.hits.bdui.domain.api.schema


/**
 * Интерфейс для представления объектов схемы маппинга API
 */
sealed interface Schema {
    val type: kotlin.String

    data class Object(
        val properties: Map<String, Schema>
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