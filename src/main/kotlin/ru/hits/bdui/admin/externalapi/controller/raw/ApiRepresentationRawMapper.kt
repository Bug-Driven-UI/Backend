package ru.hits.bdui.admin.externalapi.controller.raw

import ru.hits.bdui.domain.ApiName
import ru.hits.bdui.domain.api.ApiRepresentation
import ru.hits.bdui.domain.api.ApiRepresentationFromDatabase
import ru.hits.bdui.domain.api.ShortApiRepresentation

object ApiRepresentationRawMapper {

    fun toDomain(createRequest: ApiRepresentationCreateRequestRaw): ApiRepresentation =
        ApiRepresentation(
            name = ApiName(createRequest.name),
            description = createRequest.description,
            params = createRequest.params,
            endpoints = createRequest.endpoints,
            schema = createRequest.schema,
            mappingScript = createRequest.mappingScript
        )

    fun toDomain(updateRequest: ApiRepresentationUpdateRequestRaw): ApiRepresentation =
        ApiRepresentation(
            name = ApiName(updateRequest.api.name),
            description = updateRequest.api.description,
            params = updateRequest.api.params,
            endpoints = updateRequest.api.endpoints,
            schema = updateRequest.api.schema,
            mappingScript = updateRequest.api.mappingScript
        )

    fun fromDomain(apiRepresentation: ApiRepresentationFromDatabase): ApiRepresentationResponseRaw =
        ApiRepresentationResponseRaw(
            id = apiRepresentation.id,
            name = apiRepresentation.api.name.value,
            description = apiRepresentation.api.description,
            params = apiRepresentation.api.params,
            endpoints = apiRepresentation.api.endpoints,
            schema = apiRepresentation.api.schema ?: throw IllegalArgumentException("Schema cannot be null"),
            mappingScript = apiRepresentation.api.mappingScript ?: "",
            createdAtTimestampMs = apiRepresentation.createdAtTimestampMs,
            lastModifiedAtTimestampMs = apiRepresentation.lastModifiedTimestampMs ?: 0L
        )

    fun fromDomain(shortApiRepresentation: ShortApiRepresentation): ApiRepresentationShortRaw =
        ApiRepresentationShortRaw(
            id = shortApiRepresentation.id,
            name = shortApiRepresentation.name,
            description = shortApiRepresentation.description
        )
}
