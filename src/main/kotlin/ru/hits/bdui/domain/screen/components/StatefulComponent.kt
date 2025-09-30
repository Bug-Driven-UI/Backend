package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.Expression
import ru.hits.bdui.domain.engine.ComponentEvaluator
import ru.hits.bdui.engine.screen.component.StatefulComponentEvaluator

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 *
 * При возвращении клиенту преобразуем тип 'stateful' в 'box' и отдаем в качестве ребенка только тот компонент, условие которого выполнилось
 */
data class StatefulComponent(
    val states: List<StateDefinition>,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Component, BaseComponentProperties by basePropertiesSet {
    override val type: String = "stateful"
    override val evaluator: ComponentEvaluator = StatefulComponentEvaluator()

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}

/**
 * @property condition условие, которое должно выполниться для использования объекта из данного состояния
 */
data class StateDefinition(
    val condition: Expression,
    val component: Component
)
