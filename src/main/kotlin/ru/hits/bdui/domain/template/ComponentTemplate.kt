package ru.hits.bdui.domain.template

import ru.hits.bdui.domain.screen.components.Component

/**
 * Шаблон компонента
 */
data class ComponentTemplate(
    val name: String,
    val component: Component
)