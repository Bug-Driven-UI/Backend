package ru.hits.bdui.common.models.client.raw.styles.text

import com.fasterxml.jackson.annotation.JsonProperty

data class RenderedTextStyleRaw(
    val decoration: TextDecorationRaw?,
    val weight: Int?,
    val size: Int,
    val lineHeight: Int,
)

enum class TextDecorationRaw {
    @JsonProperty("regular") REGULAR,
    @JsonProperty("bold") BOLD,
    @JsonProperty("italic") ITALIC,
    @JsonProperty("underline") UNDERLINE,
    @JsonProperty("strikeThrough") STRIKETHROUGH,
    @JsonProperty("overline") OVERLINE,
    @JsonProperty("strikeThroughRed") STRIKETHROUGH_RED
}
