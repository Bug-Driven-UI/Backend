package ru.hits.bdui.admin.textStyles.controller.raw

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class TextStyleRaw(
    val id: UUID,
    val token: String,
    val size: Int,
    val weight: Int?,
    val lineHeight: Int,
    val decoration: TextDecorationRaw?
) {
    companion object
}

data class TextStyleForSaveRaw(
    val token: String,
    val size: Int,
    val weight: Int?,
    val lineHeight: Int,
    val decoration: TextDecorationRaw?
)

enum class TextDecorationRaw {
    @JsonProperty("regular")
    REGULAR,

    @JsonProperty("italic")
    ITALIC,

    @JsonProperty("underline")
    UNDERLINE,

    @JsonProperty("strikeThrough")
    STRIKETHROUGH,

    @JsonProperty("overline")
    OVERLINE,

    @JsonProperty("strikeThroughRed")
    STRIKETHROUGH_RED
}
