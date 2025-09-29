package ru.hits.bdui.common.models.admin.entity.components.additional

import ru.hits.bdui.common.models.admin.entity.styles.color.ColorStyleEntity

/**
 * Границы компонентов
 *
 * @property color цвет границ
 * @property thickness толщина границ
 */
data class BorderEntity(
    val color: ColorStyleEntity,
    val thickness: Int
)