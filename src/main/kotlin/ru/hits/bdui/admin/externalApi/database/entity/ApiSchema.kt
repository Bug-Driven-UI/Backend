package ru.hits.bdui.admin.externalApi.database.entity

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = ApiSchema.Object::class, name = "object"),
    JsonSubTypes.Type(value = ApiSchema.Array::class, name = "array"),
    JsonSubTypes.Type(value = ApiSchema.String::class, name = "string"),
    JsonSubTypes.Type(value = ApiSchema.Number::class, name = "number"),
)
sealed interface ApiSchema {
    val type: kotlin.String

    data class Object(
        val properties: Map<kotlin.String, ApiSchema>
    ) : ApiSchema {
        override val type = "object"
    }

    data class Array(
        val items: ApiSchema
    ) : ApiSchema {
        override val type = "array"
    }

    data object String : ApiSchema {
        override val type = "string"
    }

    data object Number : ApiSchema {
        override val type = "number"
    }
}
