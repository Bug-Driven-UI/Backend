package ru.hits.bdui.admin.externalApi.database.emerge

import ru.hits.bdui.admin.externalApi.database.entity.ApiRepresentationEntity
import ru.hits.bdui.admin.externalApi.database.entity.ApiSchema
import ru.hits.bdui.domain.api.ApiRepresentation
import ru.hits.bdui.domain.api.ApiRepresentationFromDatabase
import ru.hits.bdui.domain.api.schema.Schema
import java.util.UUID

fun ApiSchema.toDomainSchema(): Schema =
    when (this) {
        is ApiSchema.Object -> Schema.Object(
            properties = this.properties.mapValues { it.value.toDomainSchema() }
        )

        is ApiSchema.Array -> Schema.Array(
            items = this.items.toDomainSchema()
        )

        is ApiSchema.String -> Schema.String
        is ApiSchema.Number -> Schema.Number
        is ApiSchema.Boolean -> Schema.Boolean
    }

fun Schema.toApiSchema(): ApiSchema =
    when (this) {
        is Schema.Object -> ApiSchema.Object(
            properties = this.properties.mapValues { it.value.toApiSchema() }
        )

        is Schema.Array -> ApiSchema.Array(
            items = this.items.toApiSchema()
        )

        is Schema.String -> ApiSchema.String
        is Schema.Number -> ApiSchema.Number
        is Schema.Boolean -> ApiSchema.Boolean
    }

fun ApiRepresentationEntity.Companion.emerge(apiRepresentation: ApiRepresentation): ApiRepresentationEntity =
    ApiRepresentationEntity(
        id = UUID.randomUUID(),
        name = apiRepresentation.name.value,
        description = apiRepresentation.description,
        params = apiRepresentation.params,
        endpoints = apiRepresentation.endpoints,
        schema = apiRepresentation.schema?.toApiSchema(),
        mappingScript = apiRepresentation.mappingScript,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
    )

fun ApiRepresentationEntity.Companion.emerge(apiRepresentationFromDatabase: ApiRepresentationFromDatabase): ApiRepresentationEntity =
    ApiRepresentationEntity(
        id = apiRepresentationFromDatabase.id,
        name = apiRepresentationFromDatabase.api.name.value,
        description = apiRepresentationFromDatabase.api.description,
        params = apiRepresentationFromDatabase.api.params,
        endpoints = apiRepresentationFromDatabase.api.endpoints,
        schema = apiRepresentationFromDatabase.api.schema?.toApiSchema(),
        mappingScript = apiRepresentationFromDatabase.api.mappingScript,
        createdAt = apiRepresentationFromDatabase.createdAtTimestampMs,
        updatedAt = System.currentTimeMillis(),
    )

fun ApiRepresentationFromDatabase.Companion.emerge(entity: ApiRepresentationEntity): ApiRepresentationFromDatabase =
    ApiRepresentationFromDatabase(
        id = entity.id,
        api = ApiRepresentation(
            name = ru.hits.bdui.domain.ApiName(entity.name),
            description = entity.description,
            params = entity.params,
            endpoints = entity.endpoints,
            schema = entity.schema?.toDomainSchema(),
            mappingScript = entity.mappingScript,
        ),
        createdAtTimestampMs = entity.createdAt,
        lastModifiedTimestampMs = entity.updatedAt,
    )
