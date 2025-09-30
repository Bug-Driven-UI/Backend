package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.components.additional.Shape
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.styles.color.ColorStyle

/**
 * Базовый набор свойств для каждого компонента
 */
data class ComponentBaseProperties(
    val id: ComponentId,
    val interactions: List<Interaction>,
    val margins: Insets?,
    val paddings: Insets?,
    val width: Size,
    val height: Size,
    val backgroundColor: ColorStyle?,
    val border: Border?,
    val shape: Shape?
)

sealed interface Component {
    val type: String
    val base: ComponentBaseProperties
}
