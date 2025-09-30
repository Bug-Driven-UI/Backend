package ru.hits.bdui.common.models.admin.entity.components

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 */
data class StatefulComponentEntity(
    override val base: ComponentBaseEntityProperties,
    val states: List<StateDefinitionEntity>,
) : ComponentEntity {
    override val type: String = "stateful"
}

/**
 * @property condition условие, которое должно выполниться для использования объекта из данного состояния
 */
data class StateDefinitionEntity(
    val condition: String,
    val component: ComponentEntity
)
