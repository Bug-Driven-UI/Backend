package ru.hits.bdui.common.models.raw

data class ErrorResponseRaw(
    val errors: List<ErrorContentRaw>,
) {
    val type = "error"
}