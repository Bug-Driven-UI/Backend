package ru.hits.bdui.common.models.client.raw.styles.text

import ru.hits.bdui.common.models.client.raw.styles.color.RenderedColorStyleRaw

data class RenderedTextWithStyleRaw(
    val text: String,
    val textStyle: RenderedTextStyleRaw,
    val colorStyle: RenderedColorStyleRaw,
)
