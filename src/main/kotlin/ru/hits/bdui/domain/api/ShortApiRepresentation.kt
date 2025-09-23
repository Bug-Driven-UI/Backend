package ru.hits.bdui.domain.api

import ru.hits.bdui.domain.ApiName

class ShortApiRepresentation(
    val apiName: ApiName,
    val apiParams: List<String>
)