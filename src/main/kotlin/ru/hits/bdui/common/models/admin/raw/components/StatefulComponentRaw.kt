package ru.hits.bdui.common.models.admin.raw.components

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 */
data class StatefulComponentRaw(
    override val base: ComponentBaseRawProperties,
    val states: List<StateDefinitionRaw>,
) : ComponentRaw {
    override val type: String = "stateful"
}

/**
 * @property condition условие, которое должно выполниться для использования объекта из данного состояния
 */
data class StateDefinitionRaw(
    val condition: String,
    val component: ComponentRaw
)
