package ru.hits.bdui.domain.screen.components

import ru.hits.bdui.domain.engine.ComponentEvaluator
import ru.hits.bdui.engine.screen.component.CompositeEvaluator

sealed interface Composite : Component {
    val children: List<Component>
    override val evaluator: ComponentEvaluator
        get() = CompositeEvaluator()
}

data class Row(
    override val children: List<Component>,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Composite, BaseComponentProperties by basePropertiesSet {
    override val type: String = "row"

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}

data class Box(
    override val children: List<Component>,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Composite, BaseComponentProperties by basePropertiesSet {
    override val type: String = "box"

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}

data class Column(
    override val children: List<Component>,
    private val basePropertiesSet: ComponentPropertiesSet,
) : Composite, BaseComponentProperties by basePropertiesSet {
    override val type: String = "column"

    override fun copyWithNewBaseProperties(newProperties: ComponentPropertiesSet) = copy(
        basePropertiesSet = newProperties,
    )
}