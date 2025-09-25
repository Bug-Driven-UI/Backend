package ru.hits.bdui.domain.screen.components.additional

/**
 * Форма объекта
 *
 * @property topRight значение закругления правого верхнего угла
 * @property topLeft значение закругления левого верхнего угла
 * @property bottomRight значение закругления правого нижнего угла
 * @property bottomLeft значение закругления левого нижнего угла
 */
data class Shape(
    val type: ShapeType,
    val topRight: Int = 0,
    val topLeft: Int = 0,
    val bottomRight: Int = 0,
    val bottomLeft: Int = 0,
)

enum class ShapeType {
    ROUNDED_CORNERS
}