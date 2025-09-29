package ru.hits.bdui.common.models.admin.entity.components.additional

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Форма объекта
 *
 * @property topRight значение закругления правого верхнего угла
 * @property topLeft значение закругления левого верхнего угла
 * @property bottomRight значение закругления правого нижнего угла
 * @property bottomLeft значение закругления левого нижнего угла
 */
data class ShapeEntity(
    val type: ShapeTypeEntity,
    val topRight: Int = 0,
    val topLeft: Int = 0,
    val bottomRight: Int = 0,
    val bottomLeft: Int = 0,
)

enum class ShapeTypeEntity {
    @JsonProperty("roundedCorners")
    ROUNDED_CORNERS
}