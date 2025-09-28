package ru.hits.bdui.common.models.api

data class ErrorResponseRaw(
    val errors: List<ErrorContentRaw>,
) {
    val type = "error"
}