package ru.hits.bdui.admin.externalapi.controller.raw

import java.util.UUID

data class ApiRepresentationShortRaw(
    val id: UUID,
    val name: String,
    val description: String,
)
