package ru.hits.bdui.domain.screen.components.additional

import ru.hits.bdui.domain.screen.styles.ColorStyle

/**
 * Границы компонентов
 *
 * @property color цвет границ
 * @property thickness толщина границ
 */
data class Border(
    val color: ColorStyle,
    val thickness: Int
)