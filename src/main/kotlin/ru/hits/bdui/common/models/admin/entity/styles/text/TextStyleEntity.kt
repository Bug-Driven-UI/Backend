package ru.hits.bdui.common.models.admin.entity.styles.text

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TextStyleEntity(
    val token: String,
    val decoration: TextDecorationEntity?,
    val weight: Int?,
    val size: Int,
    val lineHeight: Int,
)

enum class TextDecorationEntity {
    @JsonProperty("regular")
    REGULAR,

    @JsonProperty("italic")
    ITALIC,

    @JsonProperty("underline")
    UNDERLINE,

    @JsonProperty("strikethrough")
    STRIKETHROUGH,

    @JsonProperty("overline")
    OVERLINE,

    @JsonProperty("strikethroughRed")
    STRIKETHROUGH_RED;

    companion object {
        @JvmStatic
        @JsonCreator
        fun fromValue(value: String): TextDecorationEntity {
            return entries.firstOrNull {
                it.name.equals(value, ignoreCase = true) ||
                        it.getJsonName().equals(value, ignoreCase = true)
            } ?: throw IllegalArgumentException("Неизвестное значение: $value")
        }
    }

    private fun getJsonName(): String =
        this::class.java.getField(name).getAnnotation(JsonProperty::class.java)?.value ?: name
}
