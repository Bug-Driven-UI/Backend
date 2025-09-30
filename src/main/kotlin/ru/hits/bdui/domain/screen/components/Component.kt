package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.ComponentId
import ru.hits.bdui.domain.engine.ComponentEvaluator
import ru.hits.bdui.domain.screen.components.additional.Border
import ru.hits.bdui.domain.screen.components.additional.Shape
import ru.hits.bdui.domain.screen.components.properties.Insets
import ru.hits.bdui.domain.screen.components.properties.Size
import ru.hits.bdui.domain.screen.interactions.Interaction
import ru.hits.bdui.domain.screen.styles.color.ColorStyle

sealed interface BaseComponentProperties {
    val id: ComponentId
    val interactions: List<Interaction>
    val margins: Insets?
    val paddings: Insets?
    val width: Size
    val height: Size
    val backgroundColor: ColorStyle?
    val border: Border?
    val shape: Shape?
}

data class ComponentPropertiesSet(
    override val id: ComponentId,
    override val interactions: List<Interaction>,
    override val margins: Insets?,
    override val paddings: Insets?,
    override val width: Size,
    override val height: Size,
    override val backgroundColor: ColorStyle?,
    override val border: Border?,
    override val shape: Shape?
) : BaseComponentProperties

sealed interface Component : BaseComponentProperties {
    val type: String
    val evaluator: ComponentEvaluator

    fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet): Component
}
