package ru.hits.bdui.common.models.admin.raw.components.additional

import ru.hits.bdui.common.models.admin.raw.styles.color.ColorStyleRaw

/**
 * Границы компонентов
 *
 * @property color цвет границ
 * @property thickness толщина границ
 */
data class BorderRaw(
    val color: ColorStyleRaw,
    val thickness: Int
)