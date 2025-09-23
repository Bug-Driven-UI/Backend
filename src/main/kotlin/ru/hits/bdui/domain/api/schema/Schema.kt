package ru.hits.bdui.domain.api.schema

sealed interface Schema {
    val type: String
}

data class ObjectSchema(
    val properties: Map<String, Schema>
) : Schema {
    override val type = "object"
}

data class ArraySchema(
    val items: Schema
) : Schema {
    override val type = "array"
}

data object StringSchema : Schema {
    override val type = "string"
}

data object NumberSchema : Schema {
    override val type = "number"
}