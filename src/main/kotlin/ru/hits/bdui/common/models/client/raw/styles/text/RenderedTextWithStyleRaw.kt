package ru.hits.bdui.common.models.client.raw.styles.text

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.client.raw.styles.color.RenderedColorStyleRaw

data class RenderedTextWithStyleRaw(
    val text: String,
    val textStyle: RenderedTextStyleRaw,
    val colorStyle: RenderedColorStyleRaw,
    val textAlignment: RenderedTextAlignment?
)

enum class RenderedTextAlignment {
    @JsonProperty("start")
    START,

    @JsonProperty("center")
    CENTER,

    @JsonProperty("end")
    END
}