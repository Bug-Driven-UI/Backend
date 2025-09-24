package ru.hits.bdui.domain.api

import ru.hits.bdui.domain.ApiName

data class ShortApiRepresentation(
    val apiName: ApiName,
    val apiParams: List<String>
)