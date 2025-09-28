package ru.hits.bdui.model.api

import java.util.UUID

data class ApiRepresentationUpdateModel(
    val apiId: UUID,
    val api: ApiRepresentationCreateModel,
)
