package ru.hits.bdui.admin.externalapi.controller.raw

import java.util.UUID

data class ApiRepresentationUpdateRequestRaw(
    val apiId: UUID,
    val api: ApiRepresentationCreateRequestRaw,
)
