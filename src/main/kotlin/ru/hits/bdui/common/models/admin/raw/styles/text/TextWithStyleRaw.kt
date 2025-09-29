package ru.hits.bdui.common.models.admin.raw.styles.text

import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw

data class TextWithStyleRaw(
    val text: String,
    val textStyle: TextStyleRaw,
    val color: ColorStyleRaw,
)