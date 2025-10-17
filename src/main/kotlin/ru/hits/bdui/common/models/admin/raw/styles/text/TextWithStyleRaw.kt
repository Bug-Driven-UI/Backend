package ru.hits.bdui.common.models.admin.raw.styles.text

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw

data class TextWithStyleRaw(
    val text: String,
    val textStyle: TextStyleRaw,
    val colorStyle: ColorStyleRaw,
    val textAlignment: TextAlignmentRaw?
)

enum class TextAlignmentRaw {
    @JsonProperty("start")
    START,

    @JsonProperty("center")
    CENTER,

    @JsonProperty("end")
    END
}