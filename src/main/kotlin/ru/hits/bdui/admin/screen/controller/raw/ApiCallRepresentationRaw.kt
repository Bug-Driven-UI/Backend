package ru.hits.bdui.admin.screen.controller.raw

import java.util.UUID

data class ApiCallRepresentationRaw(
    val id: UUID,
    val alias: String,
    val params: Map<String, Any>,
)