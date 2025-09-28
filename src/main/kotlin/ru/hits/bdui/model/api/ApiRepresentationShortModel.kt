package ru.hits.bdui.model.api

import java.util.UUID

data class ApiRepresentationShortModel(
    val id: UUID,
    val name: String,
    val description: String,
)
