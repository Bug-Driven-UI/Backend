package ru.hits.bdui.common.models.admin.entity.styles.text

import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity

data class TextWithStyleEntity(
    val text: String,
    val textStyle: TextStyleEntity,
    val colorStyle: ColorStyleEntity,
)