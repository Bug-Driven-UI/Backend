package ru.hits.bdui.domain.template

import ru.hits.bdui.domain.TemplateName
import ru.hits.bdui.domain.screen.components.Component

/**
 * Шаблон компонента
 *
 * @property name название шаблона
 * @property component компонент шаблона
 */
data class ComponentTemplate(
    val name: TemplateName,
    val component: Component
)