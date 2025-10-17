package ru.hits.bdui.common.models.admin.entity.styles.text

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity

data class TextWithStyleEntity(
    val text: String,
    val textStyle: TextStyleEntity,
    val colorStyle: ColorStyleEntity,
    val textAlignment: TextAlignmentEntity?
)

enum class TextAlignmentEntity {
    @JsonProperty("start")
    START,

    @JsonProperty("center")
    CENTER,

    @JsonProperty("end")
    END
}