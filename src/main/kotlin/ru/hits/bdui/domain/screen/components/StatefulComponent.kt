package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.Expression
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 *
 * При возвращении клиенту преобразуем тип 'stateful' в 'box' и отдаем в качестве ребенка только тот компонент, условие которого выполнилось
 */
data class StatefulComponent(
    override val id: ComponentId,
    override val interactions: List<Interaction> = emptyList(),
    override val insets: Insets,
    override val width: Size,
    override val height: Size,
    val states: List<StateDefinition>
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
