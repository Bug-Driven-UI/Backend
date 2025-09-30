package ru.hits.bdui.admin.screen.controller.raw

import com.fasterxml.jackson.databind.JsonNode
import java.util.UUID

data class ApiCallRepresentationRaw(
    val id: UUID,
    val alias: String,
    val params: List<ParamRaw>,
)

data class ParamRaw(
    val name: String,
    val value: JsonNode,
)