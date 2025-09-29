package ru.hits.bdui.common.models.admin.entity.styles.text

data class TextStyleEntity(
    val token: String,
    val decoration: TextDecorationEntity?,
    val weight: Int?,
    val size: Int,
    val lineHeight: Int,
)

enum class TextDecorationEntity {
    REGULAR,
    BOLD,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH,
    OVERLINE,
    STRIKETHROUGH_RED
}
