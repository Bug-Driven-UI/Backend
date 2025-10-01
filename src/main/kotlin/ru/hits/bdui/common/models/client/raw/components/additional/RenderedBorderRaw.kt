package ru.hits.bdui.common.models.client.raw.components.additional

import ru.hits.bdui.common.models.client.raw.styles.color.RenderedColorStyleRaw

/**
 * Границы компонентов
 *
 * @property color цвет границ
 * @property thickness толщина границ
 */
data class RenderedBorderRaw(
    val color: RenderedColorStyleRaw,
    val thickness: Int
)
