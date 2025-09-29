package ru.hits.bdui.common.models.admin.entity.components

/**
 * Шаблон компонента
 *
 * @property name название шаблона
 * @property component компонент шаблона
 */
data class ComponentTemplateEntity(
    val name: String,
    val component: ComponentEntity,
)