package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.Expression

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 *
 * При возвращении клиенту преобразуем тип 'stateful' в 'box' и отдаем в качестве ребенка только тот компонент, условие которого выполнилось
 */
data class StatefulComponent(
    val states: List<StateDefinition>,
    override val base: ComponentBaseProperties,
) : Component {
    override val type: String = "stateful"
}

/**
 * @property condition условие, которое должно выполниться для использования объекта из данного состояния
 */
data class StateDefinition(
    val condition: Expression,
    val component: Component
)
