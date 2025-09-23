package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.Condition
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.properties.Insets
import ru.hits.bdui.domain.screen.properties.Size

/**
 * Обертка для компонентов, которые зависят от конкретных условий
 *
 * При возвращении клиенту преобразуем тип 'stateful' в 'box'
 */
data class StatefulComponent(
    override val id: ComponentId,
    override val hash: String,
    override val interactions: List<Interaction> = emptyList(),
    override val insets: Insets,
    override val width: Size,
    override val height: Size,
    val states: List<StateDefinition>
) : Component {
    override val type: String = "stateful"
}

data class StateDefinition(
    val condition: Condition,
    val component: Component
)
